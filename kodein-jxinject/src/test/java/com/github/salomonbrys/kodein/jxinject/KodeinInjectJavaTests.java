package com.github.salomonbrys.kodein.jxinject;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KodeinInjectJavaTests {

    public static class Test00_0 {
        @Inject String firstname;
    }

    @Test
    public void test00_0_SimpleInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test0());

        Test00_0 test = new Test00_0();
        injector.javaxInject(test);

        assertEquals("Salomon", test.firstname);
    }

    public static class Test00_1 {
        public @Inject @Named("lastname") String lastname;
        public @Inject @Named("nope") @Optional String unknown;
    }

    @Test
    public void test00_1_NamedInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test0());

        Test00_1 test = new Test00_1();
        injector.javaxInject(test);

        assertEquals("BRYS", test.lastname);
        assertNull(test.unknown);
    }

    public static class Test00_2 {
        public @Inject Lazy<String> firstname;
        public @Inject @Named("nope") @Optional Lazy<String> unknown;
    }

    @Test
    public void test00_2_LazyInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test0());

        Test00_2 test = new Test00_2();
        injector.javaxInject(test);

        assertEquals("Salomon", test.firstname.getValue());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class Test00_3 {
        boolean passed = false;
        @Inject void inject(
                String firstname,
                @Named("lastname") String lastname,
                @Named("nope") @Optional String unknown,
                Lazy<String> lazyFirstname,
                @Named("lastname") Lazy<String> lazyLastname,
                @Named("nope") @Optional Lazy<String> lazyUnknown
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
    public void test00_3_MethodInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test0());

        Test00_3 test = new Test00_3();
        injector.javaxInject(test);

        assertTrue(test.passed);
    }

    public static class Test01_0 {
        @Inject @ProviderFun Function0<String> firstname;
    }

    @Test
    public void test01_0_ProviderInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_0 test = new Test01_0();
        injector.javaxInject(test);

        assertEquals("Salomon 0", test.firstname.invoke());
        assertEquals("Salomon 1", test.firstname.invoke());
    }

    public static class Test01_1 {
        public @Inject @Named("lastname") @ProviderFun Function0<String> lastname;
        public @Inject @Named("nope") @ProviderFun @Optional Function0<String> unknown;
    }

    @Test
    public void test01_1_ProviderNamedInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_1 test = new Test01_1();
        injector.javaxInject(test);

        assertEquals("BRYS 0", test.lastname.invoke());
        assertEquals("BRYS 1", test.lastname.invoke());
        assertNull(test.unknown);
    }

    public static class Test01_2 {
        public @Inject Provider<String> firstname;
    }

    @Test
    public void test01_2_JavaxProviderInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_2 test = new Test01_2();
        injector.javaxInject(test);

        assertEquals("Salomon 0", test.firstname.get());
        assertEquals("Salomon 1", test.firstname.get());
    }

    public static class Test01_3 {
        public @Inject @Named("lastname") Provider<String> lastname;
        public @Inject @Named("nope") @Optional Provider<String> unknown;
    }

    @Test
    public void test01_3_JavaxProviderNamedInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_3 test = new Test01_3();
        injector.javaxInject(test);

        assertEquals("BRYS 0", test.lastname.get());
        assertEquals("BRYS 1", test.lastname.get());
        assertNull(test.unknown);
    }

    public static class Test01_4 {
        public @Inject @ProviderFun Lazy<Function0<String>> firstname;
        public @Inject @Named("nope") @Optional @ProviderFun Lazy<Function0<String>> unknown;
    }

    @Test
    public void test01_4_LazyProviderInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_4 test = new Test01_4();
        injector.javaxInject(test);

        assertEquals("Salomon 0", test.firstname.getValue().invoke());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class Test01_5 {
        public @Inject Lazy<Provider<String>> firstname;
        public @Inject @Named("nope") @Optional Lazy<Provider<String>> unknown;
    }

    @Test
    public void test01_5_LazyJavaxProviderInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_5 test = new Test01_5();
        injector.javaxInject(test);

        assertEquals("Salomon 0", test.firstname.getValue().get());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class Test01_6 {
        boolean passed = false;
        @Inject void inject(
                @ProviderFun Function0<String> firstname,
                @Named("lastname") @ProviderFun Function0<String> lastname,
                @Named("nope") @Optional @ProviderFun Function0<String> unknown,
                @ProviderFun Lazy<Function0<String>> lazyFirstname,
                @Named("lastname") @ProviderFun Lazy<Function0<String>> lazyLastname,
                @Named("nope") @Optional @ProviderFun Lazy<Function0<String>> lazyUnknown
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
    public void test01_6_ProviderMethodInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_6 test = new Test01_6();
        injector.javaxInject(test);

        assertTrue(test.passed);
    }

    public static class Test01_7 {
        boolean passed = false;
        @Inject void inject(
                Provider<String> firstname,
                @Named("lastname") Provider<String> lastname,
                @Named("nope") @Optional Provider<String> unknown,
                Lazy<Provider<String>> lazyFirstname,
                @Named("lastname") Lazy<Provider<String>> lazyLastname,
                @Named("nope") @Optional Lazy<Provider<String>> lazyUnknown
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
    public void test01_7_JavaxProviderMethodInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());

        Test01_7 test = new Test01_7();
        injector.javaxInject(test);

        assertTrue(test.passed);
    }

    public static class Test02_0 {
        @Inject @FactoryFun Function1<Integer, String> firstname;
    }

    @Test
    public void test02_0_FactoryInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test2());

        Test02_0 test = new Test02_0();
        injector.javaxInject(test);

        assertEquals("Salomon 21", test.firstname.invoke(21));
    }

    public static class Test02_1 {
        public @Inject @Named("lastname") @FactoryFun Function1<Integer, String> lastname;
        public @Inject @Named("nope") @FactoryFun @Optional Function1<Integer, String> unknown;
    }

    @Test
    public void test02_1_ProviderNamedInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test2());

        Test02_1 test = new Test02_1();
        injector.javaxInject(test);

        assertEquals("BRYS 42", test.lastname.invoke(42));
        assertNull(test.unknown);
    }

    public static class Test02_2 {
        boolean passed = false;
        @Inject void inject(
                @FactoryFun Function1<Integer, String> firstname,
                @Named("lastname") @FactoryFun Function1<Integer, String> lastname,
                @Named("nope") @Optional @FactoryFun Function1<Integer, String> unknown,
                @FactoryFun Lazy<Function1<Integer, String>> lazyFirstname,
                @Named("lastname") @FactoryFun Lazy<Function1<Integer, String>> lazyLastname,
                @Named("nope") @Optional @FactoryFun Lazy<Function1<Integer, String>> lazyUnknown
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
    public void test02_2_FactoryMethodInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test2());

        Test02_2 test = new Test02_2();
        injector.javaxInject(test);

        assertTrue(test.passed);
    }

    public static class Test03_0 {
        @Inject
        Test03_0(
                String firstname,
                @Named("lastname") String lastname,
                @Named("nope") @Optional String unknown,
                Lazy<String> lazyFirstname,
                @Named("lastname") Lazy<String> lazyLastname,
                @Named("nope") @Optional Lazy<String> lazyUnknown
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
    public void test03_0_ConstructorInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test0());
        injector.newInstance(Test03_0.class);
    }

    public static class Test03_1 {
        @Inject
        Test03_1(
                @ProviderFun Function0<String> firstname,
                @Named("lastname") @ProviderFun Function0<String> lastname,
                @Named("nope") @Optional @ProviderFun Function0<String> unknown,
                @ProviderFun Lazy<Function0<String>> lazyFirstname,
                @Named("lastname") @ProviderFun Lazy<Function0<String>> lazyLastname,
                @Named("nope") @Optional @ProviderFun Lazy<Function0<String>> lazyUnknown
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
    public void test03_1_ConstructorProviderInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());
        injector.newInstance(Test03_1.class);
    }

    public static class Test03_2 {
        @Inject
        Test03_2(
                Provider<String> firstname,
                @Named("lastname") Provider<String> lastname,
                @Named("nope") @Optional Provider<String> unknown,
                Lazy<Provider<String>> lazyFirstname,
                @Named("lastname") Lazy<Provider<String>> lazyLastname,
                @Named("nope") @Optional Lazy<Provider<String>> lazyUnknown
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
    public void test03_2_ConstructorJavaxProviderInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test1());
        injector.newInstance(Test03_2.class);
    }

    public static class Test03_3 {
        @Inject
        Test03_3(
                @FactoryFun Function1<Integer, String> firstname,
                @Named("lastname") @FactoryFun Function1<Integer, String> lastname,
                @Named("nope") @Optional @FactoryFun Function1<Integer, String> unknown,
                @FactoryFun Lazy<Function1<Integer, String>> lazyFirstname,
                @Named("lastname") @FactoryFun Lazy<Function1<Integer, String>> lazyLastname,
                @Named("nope") @Optional @FactoryFun Lazy<Function1<Integer, String>> lazyUnknown
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
    public void test03_3_FactoryConstructorInjection() {
        KodeinJavaxInjector injector = new KodeinJavaxInjector(KodeinsKt.test2());
        injector.newInstance(Test03_3.class);
    }
}
