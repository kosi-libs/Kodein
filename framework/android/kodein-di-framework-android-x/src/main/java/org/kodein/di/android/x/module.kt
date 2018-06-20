package org.kodein.di.android.x

import android.app.Application
import android.content.Loader
import android.os.Build
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidContextGetter
import org.kodein.di.android.androidModule
import org.kodein.di.bindings.InSet
import org.kodein.di.bindings.InstanceBinding
import org.kodein.di.erased
import org.kodein.di.erasedSet

fun androidXModule(app: Application) = Kodein.Module("\u2063androidXModule") {

    importOnce(androidModule(app))

    Bind<AndroidContextGetter>(erased()).InSet(erasedSet()) with InstanceBinding(erased(), AndroidContextGetter {
        when(it) {
            is Fragment -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) it.context else it.activity
            is Loader<*> -> it.context
            else -> null
        }
    })

}
