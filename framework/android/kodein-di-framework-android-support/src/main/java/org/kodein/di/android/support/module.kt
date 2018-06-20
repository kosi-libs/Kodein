package org.kodein.di.android.support

import android.app.Application
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidContextGetter
import org.kodein.di.android.androidModule
import org.kodein.di.bindings.InSet
import org.kodein.di.bindings.InstanceBinding
import org.kodein.di.erased
import org.kodein.di.erasedSet

fun androidSupportModule(app: Application) = Kodein.Module("\u2063androidSupportModule") {

    importOnce(androidModule(app))

    Bind<AndroidContextGetter>(erased()).InSet(erasedSet()) with InstanceBinding(erased(), AndroidContextGetter {
        when(it) {
            is Fragment -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) it.context else it.activity
            is Loader<*> -> it.context
            else -> null
        }
    })

}
