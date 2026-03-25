package org.kodein.di.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.type.TypeToken
import org.kodein.type.erased
import kotlin.reflect.KClass

@PublishedApi
internal class KodeinViewModelScopedFactory<A : Any>(
    private val di: DI,
    private val argType: TypeToken<A>,
    private val vmType: TypeToken<*>,
    private val arg: A,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Factory(argType, vmType as TypeToken<T>, tag).invoke(arg)
}

@PublishedApi
internal class KodeinViewModelScopedSingleton(
    private val di: DI,
    private val vmType: TypeToken<*>,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Instance(type = vmType as TypeToken<T>, tag = tag)
}

@Deprecated("Use rememberViewModel instead.", level = DeprecationLevel.WARNING)
@Suppress("FunctionName")
public fun KodeinViewModelScopedSingleton(
    di: DI,
    tag: String? = null,
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Instance(type = erased(modelClass), tag = tag)
}

@Deprecated("Use rememberViewModel instead.", level = DeprecationLevel.WARNING)
@Suppress("FunctionName")
public fun <A : Any> KodeinViewModelScopedFactory(
    di: DI,
    argType: TypeToken<A>,
    arg: A,
    tag: String? = null,
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Factory(argType, erased(modelClass), tag).invoke(arg)
}
