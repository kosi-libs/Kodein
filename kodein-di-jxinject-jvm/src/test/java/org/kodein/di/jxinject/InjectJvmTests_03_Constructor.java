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
public class InjectJvmTests_03_Constructor {

    public static class T00 {
        @Inject
        T00(
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
    public void test_00_ConstructorInjection() {
        Jx.of(KodeinsKt.test0()).newInstance(T00.class);
    }

    public static class T01 {
        @Inject
        T01(
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
    public void test_01_ConstructorProviderInjection() {
        Jx.of(KodeinsKt.test1()).newInstance(T01.class);
    }

    public static class T02 {
        @Inject
        T02(
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
    public void test_02_ConstructorJavaxProviderInjection() {
        Jx.of(KodeinsKt.test1()).newInstance(T02.class);
    }

    public static class T03 {
        @Inject
        T03(
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
    public void test_03_FactoryConstructorInjection() {
        Jx.of(KodeinsKt.test2()).newInstance(T03.class);
    }

    public static class T04 {
        T04(
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
    public void test_04_OnlyOneConstructorInjection() {
        Jx.of(KodeinsKt.test0()).newInstance(T04.class);
    }

}
