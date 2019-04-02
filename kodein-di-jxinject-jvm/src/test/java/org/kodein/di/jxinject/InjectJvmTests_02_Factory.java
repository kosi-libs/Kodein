package org.kodein.di.jxinject;

import kotlin.Lazy;
import kotlin.jvm.functions.Function1;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InjectJvmTests_02_Factory {

    public static class T00 {
        @Inject @FactoryFun Function1<Integer, String> firstname;
    }

    @Test
    public void test_00_FactoryInjection() {
        T00 test = new T00();
        Jx.of(KodeinsKt.test2()).inject(test);

        assertEquals("Salomon 21", test.firstname.invoke(21));
    }

    public static class T01 {
        @Inject @Named("lastname") @FactoryFun Function1<Integer, String> lastname;
        @Inject @Named("nope") @FactoryFun @OrNull Function1<Integer, String> unknown;
    }

    @Test
    public void test_01_ProviderNamedInjection() {
        T01 test = new T01();
        Jx.of(KodeinsKt.test2()).inject(test);

        assertEquals("BRYS 42", test.lastname.invoke(42));
        assertNull(test.unknown);
    }

    public static class T02 {
        boolean passed = false;
        @SuppressWarnings("unused")
        @Inject
        void inject(
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
    public void test_02_FactoryMethodInjection() {
        T02 test = new T02();
        Jx.of(KodeinsKt.test2()).inject(test);

        assertTrue(test.passed);
    }

}
