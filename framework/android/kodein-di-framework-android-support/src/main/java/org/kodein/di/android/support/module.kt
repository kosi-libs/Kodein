package org.kodein.di.android.support

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.loader.content.Loader
import org.kodein.di.DI
import org.kodein.di.android.androidCoreContextTranslators
import org.kodein.di.android.androidCoreModule
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.type.generic

public val androidSupportContextTranslators: DI.Module = DI.Module("\u2063androidSupportContextTranslators") {
    importOnce(androidCoreContextTranslators)

    RegisterContextTranslator(SimpleContextTranslator<Fragment, Activity>(generic(), generic()) { it.requireActivity() })
    RegisterContextTranslator(SimpleContextTranslator<Loader<*>, Context>(generic(), generic()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<AndroidViewModel, Application>(generic(), generic()) { it.getApplication() })
}

public fun androidSupportModule(app: Application): DI.Module = DI.Module("\u2063androidSupportModule") {
    importOnce(androidSupportContextTranslators)
    importOnce(androidCoreModule(app))
}
