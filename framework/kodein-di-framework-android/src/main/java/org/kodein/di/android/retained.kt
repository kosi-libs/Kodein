package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import org.kodein.di.Kodein

/** @suppress */
class RetainedKodeinFragment : Fragment() {

    var kodein: Kodein? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

}

private const val kodeinRetainedFragmentTag = "org.kodein.di.android.RetainedKodeinFragment"

/**
 * A Kodein instance that will be retained between activity changes.
 *
 * @property allowSilentOverride Whether this module is allowed to non-explicit overrides.
 * @property init The block of configuration for this module.
 */
fun Activity.retainedKodein(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit): Lazy<Kodein> = lazy {
    (fragmentManager.findFragmentByTag(kodeinRetainedFragmentTag) as? RetainedKodeinFragment)?.kodein?.let { return@lazy it }

    val kodein = Kodein(allowSilentOverride, init)
    val fragment = RetainedKodeinFragment()
    fragment.kodein = kodein
    fragmentManager.beginTransaction().add(fragment, kodeinRetainedFragmentTag).commit()

    return@lazy kodein
}
