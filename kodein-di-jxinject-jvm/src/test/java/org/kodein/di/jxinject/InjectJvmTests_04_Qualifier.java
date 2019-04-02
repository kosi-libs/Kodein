package org.kodein.di.jxinject;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InjectJvmTests_04_Qualifier {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface UniversePrefix { String value(); }

    public static class T00 {
        @Inject
        @UniversePrefix("answer") String answer;
    }

    @Test
    public void test_00_CustomQualifier() {
        T00 test = Jx.of(KodeinsKt.test4()).newInstance(T00.class);

        assertEquals("fourty-two", test.answer);
    }

}
