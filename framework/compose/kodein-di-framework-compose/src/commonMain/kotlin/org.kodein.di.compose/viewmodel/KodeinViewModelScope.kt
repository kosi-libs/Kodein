package org.kodein.di.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.type.TypeToken
import org.kodein.type.erased
import kotlin.reflect.KClass

/**
 * Factory class for creating ViewModel instances using Kodein dependency injection.
 *
 * @param A The type of argument to be passed to the ViewModel constructor.
 * @property di The instance of the Kodein DI container.
 * @property argType The TypeToken of the argument type.
 * @property arg The argument value to be passed to the ViewModel constructor.
 * @property tag The optional tag to be used for resolving ViewModel instance from DI container.
 */
public class KodeinViewModelScopedFactory<A : Any>(
    private val di: DI,
    private val argType: TypeToken<A>,
    private val arg: A,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Factory(argType, erased(modelClass), tag).invoke(arg)
}

/**
 * Factory class used to create ViewModel instances with Kodein DI container
 * @param di The Kodein DI container instance
 * @param tag An optional tag to filter the bindings
 *
 * @throws DI.NotFoundException if ViewModel class binding is not found in the DI container
 * @see ViewModelProvider.Factory
 */
public class KodeinViewModelScopedSingleton(
    private val di: DI,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Instance(type = erased(modelClass), tag = tag)
}
