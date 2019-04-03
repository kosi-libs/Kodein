package org.kodein.di.jxinject;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InjectJvmTests_01_Provider {

    public static class T00 {
        @Inject @ProviderFun Function0<String> firstname;
    }

    @Test
    public void test_00_ProviderInjection() {
        T00 test = new T00();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.invoke());
        assertEquals("Salomon 1", test.firstname.invoke());
    }

    public static class T01 {
        @Inject @Named("lastname") @ProviderFun Function0<String> lastname;
        @Inject @Named("nope") @ProviderFun @OrNull Function0<String> unknown;
    }

    @Test
    public void test_01_ProviderNamedInjection() {
        T01 test = new T01();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("BRYS 0", test.lastname.invoke());
        assertEquals("BRYS 1", test.lastname.invoke());
        assertNull(test.unknown);
    }

    public static class T02 {
        @Inject Provider<String> firstname;
    }

    @Test
    public void test_02_JavaxProviderInjection() {
        T02 test = new T02();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.get());
        assertEquals("Salomon 1", test.firstname.get());
    }

    public static class T03 {
        @Inject @Named("lastname") Provider<String> lastname;
        @Inject @Named("nope") @OrNull Provider<String> unknown;
    }

    @Test
    public void test_03_JavaxProviderNamedInjection() {
        T03 test = new T03();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("BRYS 0", test.lastname.get());
        assertEquals("BRYS 1", test.lastname.get());
        assertNull(test.unknown);
    }

    public static class T04 {
        @Inject @ProviderFun Lazy<Function0<String>> firstname;
        @Inject @Named("nope") @OrNull @ProviderFun Lazy<Function0<String>> unknown;
    }

    @Test
    public void test_04_LazyProviderInjection() {
        T04 test = new T04();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.getValue().invoke());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class T05 {
        @Inject Lazy<Provider<String>> firstname;
        @Inject @Named("nope") @OrNull Lazy<Provider<String>> unknown;
    }

    @Test
    public void test_05_LazyJavaxProviderInjection() {
        T05 test = new T05();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertEquals("Salomon 0", test.firstname.getValue().get());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class T06 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject
        void inject(
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
    public void test_06_ProviderMethodInjection() {
        T06 test = new T06();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertTrue(test.passed);
    }

    public static class T07 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject
        void inject(
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
    public void test_07_JavaxProviderMethodInjection() {
        T07 test = new T07();
        Jx.of(KodeinsKt.test1()).inject(test);

        assertTrue(test.passed);
    }

}
