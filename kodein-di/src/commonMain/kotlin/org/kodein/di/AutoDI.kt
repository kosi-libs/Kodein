package org.kodein.di

import org.kodein.di.bindings.*
import org.kodein.type.TypeToken
import kotlin.reflect.KClass


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
public annotation class AutoDI {
    @Suppress("FunctionName")
    public interface Creator<D : Any, BD : BindingDIBase<*>, NBD : NoArgBindingDIBase<*>> {
        public fun _from(di: DI): D
        public fun _from(di: BindingDI<Any>): BD
        public fun _from(di: NoArgBindingDI<Any>): NBD
        public fun _required(): List<Triple<TypeToken<*>, TypeToken<*>, TypeToken<*>>>

        public object None : Creator<DI, BindingDI<Any>, NoArgBindingDI<Any>> {
            override fun _from(di: DI): DI = di
            override fun _from(di: BindingDI<Any>): BindingDI<Any> = di
            override fun _from(di: NoArgBindingDI<Any>): NoArgBindingDI<Any> = di
            override fun _required(): List<Triple<TypeToken<*>, TypeToken<*>, TypeToken<*>>> = emptyList()
        }
    }
}

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
public annotation class StringTag(val tag: String)

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
public annotation class IntTag(val tag: Int)

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
public annotation class ClassTag(val tag: KClass<*>)

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS)
public annotation class EnumTag
