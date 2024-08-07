package org.kodein.di.compose.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.type.TypeToken
import org.kodein.type.erased

@Deprecated(
    message = "Use the new Compose Multiplatform KodeinViewModelScopedFactory instead from Kodein Framework Compose",
    ReplaceWith("KodeinViewModelScopedFactory", "org.kodein.di.compose.viewmodel"),
    DeprecationLevel.WARNING
)
public class KodeinViewModelScopedFactory<A : Any>(
    private val di: DI,
    private val argType: TypeToken<A>,
    private val arg: A,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
            di.direct.Factory(argType, erased(modelClass), tag).invoke(arg)
}

@Deprecated(
    message = "Use the new Compose Multiplatform KodeinViewModelScopedSingleton instead from Kodein Framework Compose",
    ReplaceWith("KodeinViewModelScopedSingleton", "org.kodein.di.compose.viewmodel"),
    DeprecationLevel.WARNING
)
public class KodeinViewModelScopedSingleton(
    private val di: DI,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        di.direct.Instance(type = erased(modelClass), tag = tag)
}
