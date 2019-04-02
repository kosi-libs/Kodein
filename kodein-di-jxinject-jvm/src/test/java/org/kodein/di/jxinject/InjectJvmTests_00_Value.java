package org.kodein.di.jxinject;

import kotlin.Lazy;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InjectJvmTests_00_Value {

    public static class T00 {
        @Inject String firstname;
    }

    @Test
    public void test_00_SimpleInjection() {
        T00 test = new T00();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertEquals("Salomon", test.firstname);
    }

    public static class T01 {
        @Inject @Named("lastname") String lastname;
        @Inject @Named("nope") @OrNull
        String unknown;
    }

    @Test
    public void test_01_NamedInjection() {
        T01 test = new T01();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertEquals("BRYS", test.lastname);
        assertNull(test.unknown);
    }

    public static class T02 {
        @Inject Lazy<String> firstname;
        @Inject @Named("nope") @OrNull
        Lazy<String> unknown;
    }

    @Test
    public void test_02_LazyInjection() {
        T02 test = new T02();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertEquals("Salomon", test.firstname.getValue());
        assertNotNull(test.unknown);
        assertNull(test.unknown.getValue());
    }

    public static class T03 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject
        void inject(
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
    public void test_03_MethodInjection() {
        T03 test = new T03();
        Jx.of(KodeinsKt.test0()).inject(test);

        assertTrue(test.passed);
    }

}
