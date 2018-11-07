package org.kodein.di.android.x

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.loader.content.Loader
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.di.erased

fun androidXModule(app: Application) = Kodein.Module("\u2063androidXModule") {

    importOnce(androidModule(app))

    RegisterContextTranslator(SimpleContextTranslator<Fragment, Activity>(erased(), erased()) { it.requireActivity() })
    RegisterContextTranslator(SimpleContextTranslator<Loader<*>, Context>(erased(), erased()) { it.context })

}
