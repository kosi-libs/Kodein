package org.kodein.di.jxinject;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KodeinInjectJvmTests {

    public static class Test00_00 {
        @Inject String firstname;
    }

    @Test
    public void test00_00_SimpleInjection() {
        Test00_00 test = new Test00_00();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertEquals("Salomon", test.firstname);
    }

    public static class Test00_01 {
        @Inject @Named("lastname") String lastname;
        @Inject @Named("nope") @OrNull
        String unknown;
    }

    @Test
    public void test00_01_NamedInjection() {
        Test00_01 test = new Test00_01();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertEquals("BRYS", test.lastname);
        assertNull(test.unknown);
    }

    public static class Test00_02 {
        @Inject Lazy<String> firstname;
        @Inject @Named("nope") @OrNull
        Lazy<String> unknown;
    }

    @Test
    public void test00_02_LazyInjection() {
        Test00_02 test = new Test00_02();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertEquals("Salomon", test.firstname.getValue());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class Test00_03 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject void inject(
                String firstname,
                @Named("lastname") String lastname,
                @Named("nope") @OrNull String unknown,
                Lazy<String> lazyFirstname,
                @Named("lastname") Lazy<String> lazyLastname,
                @Named("nope") @OrNull Lazy<String> lazyUnknown
        ) {
            assertEquals("Salomon", firstname);
            assertEquals("BRYS", lastname);
            assertNull(unknown);
            assertEquals("Salomon", lazyFirstname.getValue());
            assertEquals("BRYS", lazyLastname.getValue());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
            passed = true;
        }
    }

    @Test
    public void test00_03_MethodInjection() {
        Test00_03 test = new Test00_03();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertTrue(test.passed);
    }

    public static class Test01_00 {
        @Inject @ProviderFun Function0<String> firstname;
    }

    @Test
    public void test01_00_ProviderInjection() {
        Test01_00 test = new Test01_00();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.invoke());
        assertEquals("Salomon 1", test.firstname.invoke());
    }

    public static class Test01_01 {
        @Inject @Named("lastname") @ProviderFun Function0<String> lastname;
        @Inject @Named("nope") @ProviderFun @OrNull
        Function0<String> unknown;
    }

    @Test
    public void test01_01_ProviderNamedInjection() {
        Test01_01 test = new Test01_01();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("BRYS 0", test.lastname.invoke());
        assertEquals("BRYS 1", test.lastname.invoke());
        assertNull(test.unknown);
    }

    public static class Test01_02 {
        @Inject Provider<String> firstname;
    }

    @Test
    public void test01_02_JavaxProviderInjection() {
        Test01_02 test = new Test01_02();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.get());
        assertEquals("Salomon 1", test.firstname.get());
    }

    public static class Test01_03 {
        @Inject @Named("lastname") Provider<String> lastname;
        @Inject @Named("nope") @OrNull
        Provider<String> unknown;
    }

    @Test
    public void test01_03_JavaxProviderNamedInjection() {
        Test01_03 test = new Test01_03();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("BRYS 0", test.lastname.get());
        assertEquals("BRYS 1", test.lastname.get());
        assertNull(test.unknown);
    }

    public static class Test01_04 {
        @Inject @ProviderFun Lazy<Function0<String>> firstname;
        @Inject @Named("nope") @OrNull
        @ProviderFun Lazy<Function0<String>> unknown;
    }

    @Test
    public void test01_04_LazyProviderInjection() {
        Test01_04 test = new Test01_04();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.getValue().invoke());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class Test01_05 {
        @Inject Lazy<Provider<String>> firstname;
        @Inject @Named("nope") @OrNull
        Lazy<Provider<String>> unknown;
    }

    @Test
    public void test01_05_LazyJavaxProviderInjection() {
        Test01_05 test = new Test01_05();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.getValue().get());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class Test01_06 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject void inject(
                @ProviderFun Function0<String> firstname,
                @Named("lastname") @ProviderFun Function0<String> lastname,
                @Named("nope") @OrNull @ProviderFun Function0<String> unknown,
                @ProviderFun Lazy<Function0<String>> lazyFirstname,
                @Named("lastname") @ProviderFun Lazy<Function0<String>> lazyLastname,
                @Named("nope") @OrNull @ProviderFun Lazy<Function0<String>> lazyUnknown
        ) {
            assertEquals("Salomon 0", firstname.invoke());
            assertEquals("BRYS 1", lastname.invoke());
            assertNull(unknown);
            assertEquals("Salomon 2", lazyFirstname.getValue().invoke());
            assertEquals("BRYS 3", lazyLastname.getValue().invoke());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
            passed = true;
        }
    }

    @Test
    public void test01_06_ProviderMethodInjection() {
        Test01_06 test = new Test01_06();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertTrue(test.passed);
    }

    public static class Test01_07 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject void inject(
                Provider<String> firstname,
                @Named("lastname") Provider<String> lastname,
                @Named("nope") @OrNull Provider<String> unknown,
                Lazy<Provider<String>> lazyFirstname,
                @Named("lastname") Lazy<Provider<String>> lazyLastname,
                @Named("nope") @OrNull Lazy<Provider<String>> lazyUnknown
        ) {
            assertEquals("Salomon 0", firstname.get());
            assertEquals("BRYS 1", lastname.get());
            assertNull(unknown);
            assertEquals("Salomon 2", lazyFirstname.getValue().get());
            assertEquals("BRYS 3", lazyLastname.getValue().get());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
            passed = true;
        }
    }

    @Test
    public void test01_07_JavaxProviderMethodInjection() {
        Test01_07 test = new Test01_07();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertTrue(test.passed);
    }

    public static class Test02_00 {
        @Inject @FactoryFun Function1<Integer, String> firstname;
    }

    @Test
    public void test02_00_FactoryInjection() {
        Test02_00 test = new Test02_00();
        Jx.of(KodeinsKt.test2()).inject(test);

        assertEquals("Salomon 21", test.firstname.invoke(21));
    }

    public static class Test02_01 {
        @Inject @Named("lastname") @FactoryFun Function1<Integer, String> lastname;
        @Inject @Named("nope") @FactoryFun @OrNull
        Function1<Integer, String> unknown;
    }

    @Test
    public void test02_01_ProviderNamedInjection() {
        Test02_01 test = new Test02_01();
        Jx.of(KodeinsKt.test2()).inject(test);

        assertEquals("BRYS 42", test.lastname.invoke(42));
        assertNull(test.unknown);
    }

    public static class Test02_02 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject void inject(
                @FactoryFun Function1<Integer, String> firstname,
                @Named("lastname") @FactoryFun Function1<Integer, String> lastname,
                @Named("nope") @OrNull @FactoryFun Function1<Integer, String> unknown,
                @FactoryFun Lazy<Function1<Integer, String>> lazyFirstname,
                @Named("lastname") @FactoryFun Lazy<Function1<Integer, String>> lazyLastname,
                @Named("nope") @OrNull @FactoryFun Lazy<Function1<Integer, String>> lazyUnknown
        ) {
            assertEquals("Salomon 21", firstname.invoke(21));
            assertEquals("BRYS 42", lastname.invoke(42));
            assertNull(unknown);
            assertEquals("Salomon 63", lazyFirstname.getValue().invoke(63));
            assertEquals("BRYS 84", lazyLastname.getValue().invoke(84));
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
            passed = true;
        }
    }

    @Test
    public void test02_02_FactoryMethodInjection() {
        Test02_02 test = new Test02_02();
        Jx.of(KodeinsKt.test2()).inject(test);

        assertTrue(test.passed);
    }

    public static class Test03_00 {
        @Inject
        Test03_00(
                String firstname,
                @Named("lastname") String lastname,
                @Named("nope") @OrNull String unknown,
                Lazy<String> lazyFirstname,
                @Named("lastname") Lazy<String> lazyLastname,
                @Named("nope") @OrNull Lazy<String> lazyUnknown
        ) {
            assertEquals("Salomon", firstname);
            assertEquals("BRYS", lastname);
            assertNull(unknown);
            assertEquals("Salomon", lazyFirstname.getValue());
            assertEquals("BRYS", lazyLastname.getValue());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
        }
    }

    @Test
    public void test03_00_ConstructorInjection() {
        Jx.of(KodeinsKt.test0()).newInstance(Test03_00.class);
    }

    public static class Test03_01 {
        @Inject
        Test03_01(
                @ProviderFun Function0<String> firstname,
                @Named("lastname") @ProviderFun Function0<String> lastname,
                @Named("nope") @OrNull @ProviderFun Function0<String> unknown,
                @ProviderFun Lazy<Function0<String>> lazyFirstname,
                @Named("lastname") @ProviderFun Lazy<Function0<String>> lazyLastname,
                @Named("nope") @OrNull @ProviderFun Lazy<Function0<String>> lazyUnknown
        ) {
            assertEquals("Salomon 0", firstname.invoke());
            assertEquals("BRYS 1", lastname.invoke());
            assertNull(unknown);
            assertEquals("Salomon 2", lazyFirstname.getValue().invoke());
            assertEquals("BRYS 3", lazyLastname.getValue().invoke());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
        }
    }

    @Test
    public void test03_01_ConstructorProviderInjection() {
        Jx.of(KodeinsKt.test1()).newInstance(Test03_01.class);
    }

    public static class Test03_02 {
        @Inject
        Test03_02(
                Provider<String> firstname,
                @Named("lastname") Provider<String> lastname,
                @Named("nope") @OrNull Provider<String> unknown,
                Lazy<Provider<String>> lazyFirstname,
                @Named("lastname") Lazy<Provider<String>> lazyLastname,
                @Named("nope") @OrNull Lazy<Provider<String>> lazyUnknown
        ) {
            assertEquals("Salomon 0", firstname.get());
            assertEquals("BRYS 1", lastname.get());
            assertNull(unknown);
            assertEquals("Salomon 2", lazyFirstname.getValue().get());
            assertEquals("BRYS 3", lazyLastname.getValue().get());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
        }
    }

    @Test
    public void test03_02_ConstructorJavaxProviderInjection() {
        Jx.of(KodeinsKt.test1()).newInstance(Test03_02.class);
    }

    public static class Test03_03 {
        @Inject
        Test03_03(
                @FactoryFun Function1<Integer, String> firstname,
                @Named("lastname") @FactoryFun Function1<Integer, String> lastname,
                @Named("nope") @OrNull @FactoryFun Function1<Integer, String> unknown,
                @FactoryFun Lazy<Function1<Integer, String>> lazyFirstname,
                @Named("lastname") @FactoryFun Lazy<Function1<Integer, String>> lazyLastname,
                @Named("nope") @OrNull @FactoryFun Lazy<Function1<Integer, String>> lazyUnknown
        ) {
            assertEquals("Salomon 21", firstname.invoke(21));
            assertEquals("BRYS 42", lastname.invoke(42));
            assertNull(unknown);
            assertEquals("Salomon 63", lazyFirstname.getValue().invoke(63));
            assertEquals("BRYS 84", lazyLastname.getValue().invoke(84));
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
        }
    }

    @Test
    public void test03_03_FactoryConstructorInjection() {
        Jx.of(KodeinsKt.test2()).newInstance(Test03_03.class);
    }

    public static class Test03_04 {
        Test03_04(
                String firstname,
                @Named("lastname") String lastname,
                @Named("nope") @OrNull String unknown,
                Lazy<String> lazyFirstname,
                @Named("lastname") Lazy<String> lazyLastname,
                @Named("nope") @OrNull Lazy<String> lazyUnknown
        ) {
            assertEquals("Salomon", firstname);
            assertEquals("BRYS", lastname);
            assertNull(unknown);
            assertEquals("Salomon", lazyFirstname.getValue());
            assertEquals("BRYS", lazyLastname.getValue());
            assertNotNull(lazyUnknown);
            assertNull(lazyUnknown.getValue());
        }
    }

    @Test
    public void test03_04_OnlyOneConstructorInjection() {
        Jx.of(KodeinsKt.test0()).newInstance(Test03_04.class);
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Test04_00_UniversePrefix { String value(); }

    public static class Test04_00 {
        @Inject @Test04_00_UniversePrefix("answer") String answer;
    }

    @Test
    public void test04_00_CustomQualifier() {
        Test04_00 test = Jx.of(KodeinsKt.test4()).newInstance(Test04_00.class);

        assertEquals("fourty-two", test.answer);
    }

}
