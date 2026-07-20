package org.kodein.di.jxinject;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Regression tests for https://github.com/kosi-libs/Kodein/issues/508
 *
 * On Android, an app compiles against a recent SDK but runs against the device's framework. A class
 * introduced in API 31 such as {@code android.app.PictureInPictureUiState} is simply absent at runtime on an
 * API 30 device. That is normally harmless, because ART only fails when such a method is actually executed.
 * Reflection defeats that leniency: {@code getDeclaredMethods()} / {@code getDeclaredFields()} eagerly resolve
 * the types in every member's signature, so a single unresolvable type makes the whole enumeration throw
 * {@link NoClassDefFoundError} -- even for members that carry no {@code @Inject} annotation.
 *
 * Since androidx.activity 1.13.0, {@code ComponentActivity} declares
 * {@code onPictureInPictureUiStateChanged(android.app.PictureInPictureUiState)} directly, so walking the
 * superclass chain of any Activity hits exactly that.
 *
 * These tests reproduce the failure on a plain JVM with a class loader that refuses to resolve one type.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InjectJvmTests_05_LenientReflection {

    /** Loads {@code reloaded} classes itself, refuses to resolve {@code hidden}, delegates everything else. */
    private static final class HidingClassLoader extends ClassLoader {
        private final String hidden;
        private final Set<String> reloaded;

        HidingClassLoader(ClassLoader parent, String hidden, String... reloaded) {
            super(parent);
            this.hidden = hidden;
            this.reloaded = new HashSet<>(Arrays.asList(reloaded));
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (name.equals(hidden))
                throw new ClassNotFoundException(name + " does not exist at runtime (mimics an API level too low)");

            synchronized (getClassLoadingLock(name)) {
                Class<?> cls = findLoadedClass(name);
                if (cls == null) {
                    if (reloaded.contains(name)) {
                        byte[] bytes = readBytecode(name);
                        cls = defineClass(name, bytes, 0, bytes.length);
                    } else {
                        cls = getParent().loadClass(name);
                    }
                }
                if (resolve) resolveClass(cls);
                return cls;
            }
        }

        private byte[] readBytecode(String name) throws ClassNotFoundException {
            String path = name.replace('.', '/') + ".class";
            try (InputStream in = getParent().getResourceAsStream(path)) {
                if (in == null) throw new ClassNotFoundException(name);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
                return out.toByteArray();
            } catch (IOException e) {
                throw new ClassNotFoundException(name, e);
            }
        }
    }

    private static final String MISSING = "org.kodein.di.jxinject.LenientMissing";
    private static final String METHOD_BASE = "org.kodein.di.jxinject.LenientMethodBase";
    private static final String METHOD_SUB = "org.kodein.di.jxinject.LenientMethodSub";
    private static final String FIELD_BASE = "org.kodein.di.jxinject.LenientFieldBase";
    private static final String FIELD_SUB = "org.kodein.di.jxinject.LenientFieldSub";

    private static Class<?> loadHiding(String name) throws Exception {
        ClassLoader loader = new HidingClassLoader(
                InjectJvmTests_05_LenientReflection.class.getClassLoader(),
                MISSING,
                METHOD_BASE, METHOD_SUB, FIELD_BASE, FIELD_SUB
        );
        return loader.loadClass(name);
    }

    /** These classes are package-private but loaded by another loader, so they land in a different runtime package. */
    private static Object instantiate(String name) throws Exception {
        Constructor<?> constructor = loadHiding(name).getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static Object readField(Object receiver, String name) throws Exception {
        Field field = receiver.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(receiver);
    }

    /**
     * Proves the harness is faithful: enumerating members of a class whose signature references an
     * unresolvable type throws, exactly as ART does below API 31. If this test ever stops throwing, the two
     * tests below are passing vacuously and prove nothing.
     */
    @Test
    public void test_00_HarnessActuallyFailsToEnumerateMembers() throws Exception {
        Class<?> methodBase = loadHiding(METHOD_BASE);
        assertThrows(NoClassDefFoundError.class, methodBase::getDeclaredMethods);

        Class<?> fieldBase = loadHiding(FIELD_BASE);
        assertThrows(NoClassDefFoundError.class, fieldBase::getDeclaredFields);
    }

    /** The reported case: a superclass declares a non-@Inject method taking an absent type. */
    @Test
    public void test_01_InjectsDespiteUnresolvableSuperclassMethod() throws Exception {
        Object receiver = instantiate(METHOD_SUB);

        Jx.of(KodeinsKt.test0()).inject(receiver);

        assertEquals("Salomon", readField(receiver, "firstname"));
    }

    /** The same hazard on the field enumeration path. */
    @Test
    public void test_02_InjectsDespiteUnresolvableSuperclassField() throws Exception {
        Object receiver = instantiate(FIELD_SUB);

        Jx.of(KodeinsKt.test0()).inject(receiver);

        assertEquals("Salomon", readField(receiver, "firstname"));
    }

    /**
     * Skipping a class level must not be silent: an @Inject member that is never injected leaves a null field with
     * nothing pointing at Kodein, which is harder to diagnose than a crash.
     */
    @Test
    public void test_03_WarnsWhenSkippingAClassLevel() throws Exception {
        List<LogRecord> records = new ArrayList<>();
        Handler capture = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() {}
        };

        Logger logger = Logger.getLogger("org.kodein.di.jxinject");
        logger.addHandler(capture);
        try {
            Object receiver = instantiate(METHOD_SUB);

            Jx.of(KodeinsKt.test0()).inject(receiver);

            // Logging must not have changed the outcome.
            assertEquals("Salomon", readField(receiver, "firstname"));
        } finally {
            logger.removeHandler(capture);
        }

        assertEquals(1, records.size());
        LogRecord record = records.get(0);
        assertEquals(Level.WARNING, record.getLevel());
        assertTrue(
                "message should name the skipped class, was: " + record.getMessage(),
                record.getMessage().contains(METHOD_BASE)
        );
        // The cause must travel as structured data, not be flattened into the message.
        assertTrue(record.getThrown() instanceof NoClassDefFoundError);
    }

    /**
     * The logger name is documented for users to configure, and Android derives the logcat tag from it, using it
     * verbatim only while it stays within 23 characters. Both make it a compatibility surface, not an internal detail.
     */
    @Test
    public void test_04_LoggerNameIsAUsableLogcatTag() {
        assertTrue("org.kodein.di.jxinject".length() <= 23);
    }
}

/** Stands in for {@code android.app.PictureInPictureUiState}: present at compile time, absent at runtime. */
class LenientMissing {
}

/** Stands in for {@code androidx.activity.ComponentActivity}. Note: no {@code @Inject} anywhere. */
class LenientMethodBase {
    public void onSomethingUiStateChanged(LenientMissing state) {
    }
}

class LenientMethodSub extends LenientMethodBase {
    @Inject
    String firstname;
}

class LenientFieldBase {
    LenientMissing state;
}

class LenientFieldSub extends LenientFieldBase {
    @Inject
    String firstname;
}
