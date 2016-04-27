package com.github.salomonbrys.kodein;

import com.github.salomonbrys.kodein.internal.KodeinContainer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import java.lang.reflect.Type;

/**
 * Java access to Kodein dependency injection.
 *
 * Each method works either with a Class or with a TypeToken.
 * To create a TypeToken, you must use the following syntax: `new TypeToken&lt;Type&lt;SubType&gt;&gt;(){}`
 */
public class JKodein {

    private final KodeinContainer _container;

    public JKodein(KodeinContainer container) {
        _container = container;
    }

    private <A, T> Function1<A, T> _nonNullFactory(Type argType, Type type, Object tag) {
        return _container.<A, T>nonNullFactory(new Kodein.Key(new Kodein.Bind(type, tag), argType));
    }
    public <A, T> Function1<A, T> factory(Class<A> argType, Class<T> type, Object tag)         { return _nonNullFactory(argType, type, tag); }
    public <A, T> Function1<A, T> factory(Class<A> argType, Class<T> type)                     { return _nonNullFactory(argType, type, null); }
    public <A, T> Function1<A, T> factory(TypeToken<A> argType, TypeToken<T> type, Object tag) { return _nonNullFactory(argType.getType(), type.getType(), tag); }
    public <A, T> Function1<A, T> factory(TypeToken<A> argType, TypeToken<T> type)             { return _nonNullFactory(argType.getType(), type.getType(), null); }
    public <A, T> Function1<A, T> factory(Class<A> argType, TypeToken<T> type, Object tag)     { return _nonNullFactory(argType, type.getType(), tag); }
    public <A, T> Function1<A, T> factory(Class<A> argType, TypeToken<T> type)                 { return _nonNullFactory(argType, type.getType(), null); }
    public <A, T> Function1<A, T> factory(TypeToken<A> argType, Class<T> type, Object tag)     { return _nonNullFactory(argType.getType(), type, tag); }
    public <A, T> Function1<A, T> factory(TypeToken<A> argType, Class<T> type)                 { return _nonNullFactory(argType.getType(), type, null); }

    private <A, T> Function1<A, T> _factoryOrNull(Type argType, Type type, Object tag) {
        return _container.<A, T>factoryOrNull(new Kodein.Key(new Kodein.Bind(type, tag), argType));
    }
    public <A, T> Function1<A, T> factoryOrNull(Class<A> argType, Class<T> type, Object tag)         { return _factoryOrNull(argType, type, tag); }
    public <A, T> Function1<A, T> factoryOrNull(Class<A> argType, Class<T> type)                     { return _factoryOrNull(argType, type, null); }
    public <A, T> Function1<A, T> factoryOrNull(TypeToken<A> argType, TypeToken<T> type, Object tag) { return _factoryOrNull(argType.getType(), type.getType(), tag); }
    public <A, T> Function1<A, T> factoryOrNull(TypeToken<A> argType, TypeToken<T> type)             { return _factoryOrNull(argType.getType(), type.getType(), null); }
    public <A, T> Function1<A, T> factoryOrNull(Class<A> argType, TypeToken<T> type, Object tag)     { return _factoryOrNull(argType, type.getType(), tag); }
    public <A, T> Function1<A, T> factoryOrNull(Class<A> argType, TypeToken<T> type)                 { return _factoryOrNull(argType, type.getType(), null); }
    public <A, T> Function1<A, T> factoryOrNull(TypeToken<A> argType, Class<T> type, Object tag)     { return _factoryOrNull(argType.getType(), type, tag); }
    public <A, T> Function1<A, T> factoryOrNull(TypeToken<A> argType, Class<T> type)                 { return _factoryOrNull(argType.getType(), type, null); }

    private <T> Function0<T> _nonNullprovider(Type type, Object tag) {
        return _container.<T>nonNullProvider(new Kodein.Bind(type, tag));
    }
    public <T> Function0<T> provider(Class<T> type, Object tag)     { return _nonNullprovider(type, tag); }
    public <T> Function0<T> provider(Class<T> type)                 { return _nonNullprovider(type, null); }
    public <T> Function0<T> provider(TypeToken<T> type, Object tag) { return _nonNullprovider(type.getType(), tag); }
    public <T> Function0<T> provider(TypeToken<T> type)             { return _nonNullprovider(type.getType(), null); }

    private <T> Function0<T> _providerOrNull(Type type, Object tag) {
        return _container.<T>providerOrNull(new Kodein.Bind(type, tag));
    }
    public <T> Function0<T> providerOrNull(Class<T> type, Object tag)     { return _providerOrNull(type, tag); }
    public <T> Function0<T> providerOrNull(Class<T> type)                 { return _providerOrNull(type, null); }
    public <T> Function0<T> providerOrNull(TypeToken<T> type, Object tag) { return _providerOrNull(type.getType(), tag); }
    public <T> Function0<T> providerOrNull(TypeToken<T> type)             { return _providerOrNull(type.getType(), null); }

    private <T> T _nonNullInstance(Type type, Object tag) {
        return _container.<T>nonNullProvider(new Kodein.Bind(type, tag)).invoke();
    }
    public <T> T instance(Class<T> type, Object tag)     { return _nonNullInstance(type, tag); }
    public <T> T instance(Class<T> type)                 { return _nonNullInstance(type, null); }
    public <T> T instance(TypeToken<T> type, Object tag) { return _nonNullInstance(type.getType(), tag); }
    public <T> T instance(TypeToken<T> type)             { return _nonNullInstance(type.getType(), null); }

    private <T> T _instanceOrNull(Type type, Object tag) {
        Function0<T> f = _container.<T>providerOrNull(new Kodein.Bind(type, tag));
        if (f == null) return null;
        return f.invoke();
    }
    public <T> T instanceOrNull(Class<T> type, Object tag)     { return _instanceOrNull(type, tag); }
    public <T> T instanceOrNull(Class<T> type)                 { return _instanceOrNull(type, null); }
    public <T> T instanceOrNull(TypeToken<T> type, Object tag) { return _instanceOrNull(type.getType(), tag); }
    public <T> T instanceOrNull(TypeToken<T> type)             { return _instanceOrNull(type.getType(), null); }
}
