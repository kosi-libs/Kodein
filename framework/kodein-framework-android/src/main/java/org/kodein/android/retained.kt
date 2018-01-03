package org.kodein.android

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import org.kodein.Kodein

@SuppressLint("ValidFragment")
private class KodeinFragment(val kodein: Kodein) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

}

private const val _kodeinFragmentTag = "org.kodein.android.KodeinFragment"

fun Activity.retainedKodein(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit): Lazy<Kodein> = lazy {
    (fragmentManager.findFragmentByTag(_kodeinFragmentTag) as? KodeinFragment)?.let { return@lazy it.kodein }

    val fragment = KodeinFragment(Kodein(allowSilentOverride, init))
    fragmentManager.beginTransaction().add(fragment, _kodeinFragmentTag).commit()

    return@lazy fragment.kodein
}
