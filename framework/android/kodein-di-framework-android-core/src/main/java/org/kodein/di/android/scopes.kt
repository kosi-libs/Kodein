@file:Suppress("DEPRECATION")

package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import org.kodein.di.bindings.*
import java.lang.ref.WeakReference

private const val SCOPE_FRAGMENT_TAG = "org.kodein.android.ActivityRetainedScope.RetainedScopeFragment"

/**
 * A scope that allows to get an activity-scoped singleton that's independent from the activity restart.
 */
public open class ActivityRetainedScope private constructor(private val registryType: RegistryType) : Scope<Activity> {

    private enum class RegistryType {
        Standard { override fun new() = StandardScopeRegistry() },
        SingleItem { override fun new() = SingleItemScopeRegistry() };
        abstract fun new(): ScopeRegistry
    }

    public object Keys {
        public const val registryTypeOrdinal: String = "org.kodein.di.android.registryTypeOrdinal"
    }

    public companion object MultiItem: ActivityRetainedScope(RegistryType.Standard)

    public object SingleItem: ActivityRetainedScope(RegistryType.SingleItem)

    /** @suppress */
    public class RetainedScopeFragment: Fragment() {
        public val registry: ScopeRegistry by lazy {
            val ordinal = arguments.getInt(Keys.registryTypeOrdinal)
            RegistryType.entries[ordinal].new()
        }

        public var transactionPendingFragmentCache: MutableMap<Activity, WeakReference<RetainedScopeFragment>>? = null

        @Deprecated("Deprecated in Java")
        override fun onAttach(context: Context?) {
            super.onAttach(context)
            transactionPendingFragmentCache?.remove(context)
            transactionPendingFragmentCache = null
        }

        @Deprecated("Deprecated in Java")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        @Deprecated("Deprecated in Java")
        override fun onDestroy() {
            registry.clear()
            super.onDestroy()
        }
    }

    // This is a hack to circumvent the fact that commitNow do not exist before Android N.
    // See https://github.com/Kodein-Framework/Kodein-DI/pull/174
    private val transactionPendingFragmentCache = HashMap<Activity, WeakReference<RetainedScopeFragment>>()

    override fun getRegistry(context: Activity): ScopeRegistry {
        val fragment = context.retainedScopeFragment ?: run {
            synchronized(context) {
                context.retainedScopeFragment ?: transactionPendingFragmentCache[context]?.get() ?: run {
                    RetainedScopeFragment().also {
                        it.arguments = Bundle().apply { putInt(Keys.registryTypeOrdinal, registryType.ordinal) }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            context.fragmentManager.beginTransaction().add(it, SCOPE_FRAGMENT_TAG).commitNow()
                        } else {
                            // since we can't commit immediately, we cache the fragment temporarily and clear
                            // the reference when the commit completes
                            transactionPendingFragmentCache[context] = WeakReference(it)
                            it.transactionPendingFragmentCache = transactionPendingFragmentCache
                            context.fragmentManager.beginTransaction().add(it, SCOPE_FRAGMENT_TAG).commit()
                        }
                    }
                }
            }
        }
        return fragment.registry
    }

    private val Activity.retainedScopeFragment: RetainedScopeFragment?
        get() = fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment

}
