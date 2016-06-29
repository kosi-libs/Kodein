package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Java access to Kodein dependency injection.

 * Each method works either with a Class or with a TypeToken.
 * To create a TypeToken, you must use the following syntax: `new TypeToken&lt;Type&lt;SubType&gt;&gt;(){}`
 */
@Suppress("UNCHECKED_CAST", "unused")
interface TKodein {

    fun factory(argType: Type, type: Type, tag: Any? = null): (Any) -> Any

    fun <T : Any> factory(argType: Type, type: Class<T>, tag: Any? = null): (Any) -> T = factory(argType, type as Type, tag) as (Any) -> T

    fun <T : Any> factory(argType: Type, type: TypeToken<T>, tag: Any? = null): (Any) -> T = factory(argType, type.type, tag) as (Any) -> T

    fun <A> factory(argType: Class<A>, type: Type, tag: Any? = null): (A) -> Any = factory(argType as Type, type, tag) as (A) -> Any

    fun <A, T : Any> factory(argType: Class<A>, type: Class<T>, tag: Any? = null): (A) -> T = factory(argType as Type, type as Type, tag) as (A) -> T

    fun <A, T : Any> factory(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): (A) -> T = factory(argType as Type, type.type, tag) as (A) -> T

    fun <A> factory(argType: TypeToken<A>, type: Type, tag: Any? = null): (A) -> Any = factory(argType.type, type, tag) as (A) -> Any

    fun <A, T : Any> factory(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): (A) -> T = factory(argType.type, type as Type, tag) as (A) -> T

    fun <A, T : Any> factory(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): (A) -> T = factory(argType.type, type.type, tag) as (A) -> T



    fun factoryOrNull(argType: Type, type: Type, tag: Any? = null): ((Any) -> Any)?

    fun <T : Any> factoryOrNull(argType: Type, type: Class<T>, tag: Any? = null): ((Any) -> T)? = factoryOrNull(argType, type as Type, tag) as ((Any) -> T)?

    fun <T : Any> factoryOrNull(argType: Type, type: TypeToken<T>, tag: Any? = null): ((Any) -> T)? = factoryOrNull(argType, type.type, tag) as ((Any) -> T)?

    fun <A> factoryOrNull(argType: Class<A>, type: Type, tag: Any? = null): ((A) -> Any)? = factoryOrNull(argType as Type, type, tag) as ((A) -> Any)?

    fun <A, T : Any> factoryOrNull(argType: Class<A>, type: Class<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType as Type, type as Type, tag) as ((A) -> T)?

    fun <A, T : Any> factoryOrNull(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType as Type, type.type, tag) as ((A) -> T)?

    fun <A> factoryOrNull(argType: TypeToken<A>, type: Type, tag: Any? = null): ((A) -> Any)? = factoryOrNull(argType.type, type, tag) as ((A) -> Any)?

    fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType.type, type as Type, tag) as ((A) -> T)?

    fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType.type, type.type, tag) as ((A) -> T)?



    fun provider(type: Type, tag: Any? = null): () -> Any

    fun <T : Any> provider(type: Class<T>, tag: Any? = null): () -> T = provider(type as Type, tag) as () -> T

    fun <T : Any> provider(type: TypeToken<T>, tag: Any? = null): () -> T = provider(type.type, tag) as () -> T



    fun providerOrNull(type: Type, tag: Any? = null): (() -> Any)?

    fun <T : Any> providerOrNull(type: Class<T>, tag: Any? = null): (() -> T)? = providerOrNull(type as Type, tag) as (() -> T)?

    fun <T : Any> providerOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)? = providerOrNull(type.type, tag) as (() -> T)?



    fun instance(type: Type, tag: Any? = null): Any

    fun <T : Any> instance(type: Class<T>, tag: Any? = null): T = instance(type as Type, tag) as T

    fun <T : Any> instance(type: TypeToken<T>, tag: Any? = null): T = instance(type.type, tag) as T



    fun instanceOrNull(type: Type, tag: Any? = null): Any?

    fun <T : Any> instanceOrNull(type: Class<T>, tag: Any? = null): T? = instanceOrNull(type as Type, tag) as T?

    fun <T : Any> instanceOrNull(type: TypeToken<T>, tag: Any? = null): T? = instanceOrNull(type.type, tag) as T?
}
