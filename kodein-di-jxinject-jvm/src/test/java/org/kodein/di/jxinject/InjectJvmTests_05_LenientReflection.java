package org.kodein.di.jxinject;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Regression test for <a href="https://github.com/kosi-libs/Kodein/issues/508">issue #508</a>.
 *
 * <p>Injecting into a {@code ComponentActivity} subclass crashed with a {@code NoClassDefFoundError} on
 * Android devices whose API level is older than a type referenced by one of the Activity's members (for
 * instance {@code android.app.PictureInPictureUiState}, added in API 31). {@link Class#getDeclaredMethods()}
 * and {@link Class#getDeclaredFields()} eagerly resolve the types referenced by every member's signature,
 * so a single unresolvable type makes the whole enumeration throw — even when the offending member is not
 * annotated with {@code @Inject}.
 *
 * <p>The JVM resolves member signatures eagerly just like Android's ART, so these tests reproduce the
 * failure with a class loader that refuses to resolve a "missing" type referenced by a superclass member.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InjectJvmTests_05_LenientReflection {

    /** Stand-in for a platform type that is absent at runtime on older devices. */
    public static class Missing {}

    /** Stand-in for a framework superclass exposing a method that references {@link Missing}. */
    public static class PoisonedByMethod {
        @SuppressWarnings("unused")
        public void referencesMissingType(Missing missing) {}
    }

    /** Stand-in for a framework superclass exposing a field that references {@link Missing}. */
    public static class PoisonedByField {
        @SuppressWarnings("unused")
        public Missing missing;
    }

    /** Stand-in for the user's Activity subclass carrying a valid {@code @Inject} member. */
    public static class InjectableBelowPoisonedMethod extends PoisonedByMethod {
        @Inject String firstname;
    }

    /** Stand-in for the user's Activity subclass carrying a valid {@code @Inject} member. */
    public static class InjectableBelowPoisonedField extends PoisonedByField {
        @Inject String firstname;
    }

    /**
     * Class loader that defines the given classes itself — so their member signatures resolve through it —
     * while refusing to resolve {@code blockedName}, simulating a type that is absent at runtime. Everything
     * else is delegated to the parent.
     */
    private static final class MissingTypeClassLoader extends ClassLoader {
        private final String blockedName;
        private final List<String> localNames;

        MissingTypeClassLoader(ClassLoader parent, String blockedName, String... localNames) {
            super(parent);
            this.blockedName = blockedName;
            this.localNames = Arrays.asList(localNames);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                if (name.equals(blockedName))
                    throw new ClassNotFoundException(name + " is not available at runtime");

                Class<?> loaded = findLoadedClass(name);
                if (loaded == null && localNames.contains(name))
                    loaded = defineLocally(name);
                if (loaded == null)
                    return super.loadClass(name, resolve);

                if (resolve)
                    resolveClass(loaded);
                return loaded;
            }
        }

        private Class<?> defineLocally(String name) throws ClassNotFoundException {
            String resource = name.replace('.', '/') + ".class";
            try (InputStream stream = getParent().getResourceAsStream(resource)) {
                if (stream == null)
                    throw new ClassNotFoundException(name);
                byte[] bytes = stream.readAllBytes();
                return defineClass(name, bytes, 0, bytes.length);
            } catch (ClassNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new ClassNotFoundException(name, e);
            }
        }
    }

    private static Object loadInjectableWithMissingSupertype(Class<?> injectableClass, Class<?> poisonedSupertype) throws Exception {
        MissingTypeClassLoader loader = new MissingTypeClassLoader(
                InjectJvmTests_05_LenientReflection.class.getClassLoader(),
                Missing.class.getName(),
                poisonedSupertype.getName(),
                injectableClass.getName()
        );
        return loader.loadClass(injectableClass.getName())
                .getDeclaredConstructor()
                .newInstance();
    }

    private static void assertEnumeratingMembersThrows(Class<?> poisonedSupertype) {
        try {
            poisonedSupertype.getDeclaredMethods();
            poisonedSupertype.getDeclaredFields();
            fail("Expected enumerating the members of " + poisonedSupertype + " to throw NoClassDefFoundError");
        } catch (NoClassDefFoundError expected) {
            // exactly the failure reported in #508
        }
    }

    @Test
    public void test_00_InjectionSurvivesUnresolvableSuperclassMethod() throws Exception {
        Object injectable = loadInjectableWithMissingSupertype(InjectableBelowPoisonedMethod.class, PoisonedByMethod.class);

        // Sanity check: enumerating the poisoned superclass really does blow up, so this test exercises the
        // same failure as issue #508 rather than a no-op.
        assertEnumeratingMembersThrows(injectable.getClass().getSuperclass());

        // Before the fix this call propagated the NoClassDefFoundError and injection failed entirely.
        Jx.of(KodeinsKt.test0()).inject(injectable);

        assertEquals("Salomon", readField(injectable, "firstname"));
    }

    @Test
    public void test_01_InjectionSurvivesUnresolvableSuperclassField() throws Exception {
        Object injectable = loadInjectableWithMissingSupertype(InjectableBelowPoisonedField.class, PoisonedByField.class);

        assertEnumeratingMembersThrows(injectable.getClass().getSuperclass());

        Jx.of(KodeinsKt.test0()).inject(injectable);

        assertEquals("Salomon", readField(injectable, "firstname"));
    }

    private static Object readField(Object target, String name) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }
}
