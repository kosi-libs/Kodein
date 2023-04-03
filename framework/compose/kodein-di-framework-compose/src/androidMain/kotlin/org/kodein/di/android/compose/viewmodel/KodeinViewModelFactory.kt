package org.kodein.di.android.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.type.TypeToken

public class KodeinViewModelFactory<A : Any, VM : ViewModel>(
    private val di: DI,
    private val vmType: TypeToken<VM>,
    private val argType: TypeToken<A>,
    private val arg: A,
    private val tag: Any? = null,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
            di.direct.Factory(argType, vmType, tag).invoke(arg) as T
}


public class KodeinViewModelInstance<VM : ViewModel>(
    private val di: DI,
    private val vmType: TypeToken<VM>,
    private val tag: Any? = null,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
            di.direct.Instance(type = vmType, tag = tag) as T
}
