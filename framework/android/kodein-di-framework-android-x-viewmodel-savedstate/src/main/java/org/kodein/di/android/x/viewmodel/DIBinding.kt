package org.kodein.di.android.x.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindings.Factory
import org.kodein.di.factory
import org.kodein.type.generic

/**
 * Binding shortcut for a ViewModel factory with a SavedStateHandle argument. Each time an instance is needed, the function [creator] function will be called.
 *
 * VM generics will be erased!
 *
 * @param VM The created ViewModel type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
inline fun <reified VM : ViewModel> DI.Builder.bindViewModelWithSavedStateHandle(
        tag: Any? = null,
        overrides: Boolean? = null,
        noinline creator: DirectDI.(SavedStateHandle) -> VM
): Unit = Bind(tag = tag, overrides = overrides, binding = factory(creator = creator))

/**
 * Context binding shortcut for a ViewModel factory with a SavedStateHandle argument. Each time an instance is needed, the function [creator] function will be called.
 *
 * VM generics will be erased!
 *
 * @param C The context type.
 * @param VM The created ViewModel type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
inline fun <C : Any, reified VM : ViewModel> DI.BindBuilder<C>.viewModelWithSavedStateHandle(
        noinline creator: DirectDI.(SavedStateHandle) -> VM
): Factory<C, SavedStateHandle, VM> = Factory(contextType, generic(), generic(), creator)
