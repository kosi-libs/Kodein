package org.kodein.di.android.support

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import org.kodein.di.DI
import org.kodein.di.android.androidCoreContextTranslators
import org.kodein.di.android.androidCoreModule
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.type.generic

val androidSupportContextTranslators = DI.Module("\u2063androidSupportContextTranslators") {
    importOnce(androidCoreContextTranslators)

    RegisterContextTranslator(SimpleContextTranslator<Fragment, Activity>(generic(), generic()) { it.requireActivity() })
    RegisterContextTranslator(SimpleContextTranslator<Loader<*>, Context>(generic(), generic()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<AndroidViewModel, Application>(generic(), generic()) { it.getApplication() })
}

fun androidSupportModule(app: Application) = DI.Module("\u2063androidSupportModule") {
    importOnce(androidSupportContextTranslators)
    importOnce(androidCoreModule(app))
}
