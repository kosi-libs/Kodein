package org.kodein.di.android.support

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.di.erased

fun androidSupportModule(app: Application) = Kodein.Module("\u2063androidSupportModule") {

    importOnce(androidModule(app))

    RegisterContextTranslator(SimpleContextTranslator<Fragment, Activity>(erased(), erased()) { it.requireActivity() })
    RegisterContextTranslator(SimpleContextTranslator<Loader<*>, Context>(erased(), erased()) { it.context })

}
