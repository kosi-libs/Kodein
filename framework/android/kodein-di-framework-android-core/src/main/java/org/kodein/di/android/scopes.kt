@file:Suppress("DEPRECATION")

package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import org.kodein.di.bindings.*

// Deprecated since Kodein 6.0
@Deprecated("Use WeakContextScope.of()")
object AndroidComponentsWeakScope : WeakContextScope<Any?>()

private const val SCOPE_FRAGMENT_TAG = "org.kodein.android.ActivityRetainedScope.RetainedScopeFragment"

/**
 * A scope that allows to get an activity-scoped singleton that's independent from the activity restart.
 */
open class ActivityRetainedScope private constructor(private val registryType: RegistryType) : SimpleScope<Activity> {

    private enum class RegistryType {
        Standard { override fun new() = StandardScopeRegistry() },
        SingleItem { override fun new() = SingleItemScopeRegistry() };
        abstract fun new(): ScopeRegistry
    }

    object Keys {
        const val registryTypeOrdinal = "org.kodein.di.android.registryTypeOrdinal"
    }

    companion object MultiItem: ActivityRetainedScope(RegistryType.Standard) {
        // Deprecated since 6.0
        @Deprecated("use MultiItem", replaceWith = ReplaceWith("MultiItem"))
        val multiItem = MultiItem

        // Deprecated since 6.0
        @Deprecated("use SingleItem", replaceWith = ReplaceWith("SingleItem"))
        val singleItem = SingleItem
    }

    object SingleItem: ActivityRetainedScope(RegistryType.SingleItem)

    /** @suppress */
    class RetainedScopeFragment: Fragment() {
        val registry by lazy {
            val ordinal = arguments.getInt(Keys.registryTypeOrdinal)
            RegistryType.values()[ordinal].new()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onDestroy() {
            registry.clear()
            super.onDestroy()
        }
    }

    override fun getRegistry(context: Activity): ScopeRegistry {
        val fragment = context.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
            synchronized(context) {
                context.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
                    RetainedScopeFragment().also {
                        it.arguments = Bundle().apply { putInt(Keys.registryTypeOrdinal, registryType.ordinal) }
                        context.fragmentManager.beginTransaction().add(it, SCOPE_FRAGMENT_TAG).commit()
                    }
                }
            }
        }
        return fragment.registry
    }

}
