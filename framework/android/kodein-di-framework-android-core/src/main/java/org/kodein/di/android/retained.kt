@file:Suppress("DEPRECATION")

package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import org.kodein.di.*

/** @suppress */
public class RetainedDIFragment : Fragment() {

    private var _di: DI? = null
    public var di: DI?
        get() = _di
        set(value) {
            _di = value
        }

    @Deprecated("Deprecated in Java")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

}

private const val DI_RETAINED_FRAGMENT_TAG = "org.kodein.di.android.RetainedDIFragment"

/**
 * A DI instance that will be retained between activity changes.
 *
 * @property allowSilentOverride Whether this module is allowed to non-explicit overrides.
 * @property init The block of configuration for this module.
 */
public fun Activity.retainedDI(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit): Lazy<DI> = lazy {
    (fragmentManager.findFragmentByTag(DI_RETAINED_FRAGMENT_TAG) as? RetainedDIFragment)?.di?.let { return@lazy it }

    val di = DI(allowSilentOverride, init)
    val fragment = RetainedDIFragment()
    fragment.di = di
    fragmentManager.beginTransaction().add(fragment, DI_RETAINED_FRAGMENT_TAG).commit()

    return@lazy di
}