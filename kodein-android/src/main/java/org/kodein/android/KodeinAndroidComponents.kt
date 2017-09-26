package org.kodein.android

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.app.IntentService
import android.app.LoaderManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.annotation.CallSuper
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import org.kodein.Kodein
import org.kodein.KodeinInjected
import org.kodein.KodeinInjector
import org.kodein.bindings.InstanceBinding
import org.kodein.bindings.ScopeRegistry
import org.kodein.erased
import android.support.v4.app.Fragment as SupportFragment
import android.support.v4.app.FragmentManager as SupportFragmentManager
import android.support.v4.app.LoaderManager as SupportLoaderManager

/**
 * Tag that allows to get the Activity LayoutInflater as opposed to a LayoutInflater received as a Context Service.
 */
val ACTIVITY_LAYOUT_INFLATER = Any()

private fun _inject(injector: KodeinInjected, componentModule: Kodein.Module, superKodein: Kodein) {
    val kodein = Kodein {
        extend(superKodein, allowOverride = true)

        import(componentModule, allowOverride = true)
    }

    injector.inject(kodein)
}

/**
 * An interface for adding a [KodeinInjector] to Android component classes (Activity, Fragment, Service, etc...)
 */
interface AndroidInjector<T, out S : AndroidScope<T>> : KodeinInjected {
    // this covers most of the use cases (and it really shouldn't ever be in a position where the cast doesn't work)
    /**
     * A convenient way for sub-interfaces to reference the component (for internal use only)
     */
    @Suppress("UNCHECKED_CAST")
    val kodeinComponent: T get() = this as T

    /**
     * The scope that this component belongs to (for internal use only)
     */
    val kodeinScope: S

    /**
     * Adds bindings specific for this component and injects all the properties created with the injector.
     * Should be called when the component is being created.
     */
    fun initializeInjector()

    /**
     * Removes the component from its scope. Should be called when the component is being destroyed.
     */
    @CallSuper
    fun destroyInjector() = kodeinScope.removeFromScope(kodeinComponent)

    /**
     * Allows the component to override any bindings before injection.
     */
    fun provideOverridingModule(): Kodein.Module = Kodein.Module {}
}

/**
 * An interface for adding injection and bindings to an Activity.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@Activity
 * - Context = this@Activity
 * - Activity = this@Activity
 * - FragmentManager = Activity.getFragmentManager
 * - LoaderManager = Activity.getLoaderManager
 * - LayoutInflater = Activity.getLayoutInflater
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface ActivityInjector : AndroidInjector<Activity, AndroidScope<Activity>> {
    override val kodeinScope: AndroidScope<Activity> get() = androidActivityScope

    override fun initializeInjector() {
        val activityModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@ActivityInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<Activity>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<FragmentManager>(erased()) with InstanceBinding(erased(), kodeinComponent.fragmentManager)
            Bind<LoaderManager>(erased()) with InstanceBinding(erased(), kodeinComponent.loaderManager)
            Bind<LayoutInflater>(erased(), tag = ACTIVITY_LAYOUT_INFLATER) with InstanceBinding(erased(), kodeinComponent.layoutInflater)

            import(provideOverridingModule(), allowOverride = true)
        }

        _inject(this, activityModule, kodeinComponent.appKodein())
    }
}

/**
 * A base class that manages an [ActivityInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinActivity : Activity(), ActivityInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()

        super.onCreate(savedInstanceState)
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

/**
 * An interface for adding injection and bindings to a FragmentActivity.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@FragmentActivity
 * - Context = this@FragmentActivity
 * - Activity = this@FragmentActivity
 * - FragmentActivity = this@FragmentActivity
 * - FragmentManager = FragmentActivity.getFragmentManager
 * - LoaderManager = FragmentActivity.getLoaderManager
 * - LayoutInflater = FragmentActivity.getLayoutInflater
 * - android.support.v4.app.FragmentManager = FragmentActivity.getSupportFragmentManager
 * - android.support.v4.app.LoaderManager = FragmentActivity.getSupportLoaderManager
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface FragmentActivityInjector : AndroidInjector<FragmentActivity, AndroidScope<FragmentActivity>> {
    override val kodeinScope: AndroidScope<FragmentActivity> get() = androidActivityScope

    override fun initializeInjector() {
        val activityModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@FragmentActivityInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<Activity>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<FragmentActivity>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<FragmentManager>(erased()) with InstanceBinding(erased(), kodeinComponent.fragmentManager)
            Bind<LoaderManager>(erased()) with InstanceBinding(erased(), kodeinComponent.loaderManager)
            Bind<SupportFragmentManager>(erased()) with InstanceBinding(erased(), kodeinComponent.supportFragmentManager)
            Bind<SupportLoaderManager>(erased()) with InstanceBinding(erased(), kodeinComponent.supportLoaderManager)
            Bind<LayoutInflater>(erased(), ACTIVITY_LAYOUT_INFLATER) with InstanceBinding(erased(), kodeinComponent.layoutInflater)

            import(provideOverridingModule(), allowOverride = true)
        }

        _inject(this, activityModule, kodeinComponent.appKodein())
    }
}

/**
 * A base class that manages a [FragmentActivityInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinFragmentActivity : FragmentActivity(), FragmentActivityInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()

        super.onCreate(savedInstanceState)
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

/**
 * An interface for adding injection and bindings to an AppCompatActivity.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@AppCompatActivity
 * - Context = this@AppCompatActivity
 * - Activity = this@AppCompatActivity
 * - FragmentActivity = this@AppCompatActivity
 * - AppCompatActivity = this@AppCompatActivity
 * - FragmentManager = AppCompatActivity.getFragmentManager
 * - LoaderManager = AppCompatActivity.getLoaderManager
 * - LayoutInflater = AppCompatActivity.getLayoutInflater
 * - android.support.v4.app.FragmentManager = AppCompatActivity.getSupportFragmentManager
 * - android.support.v4.app.LoaderManager = AppCompatActivity.getSupportLoaderManager
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface AppCompatActivityInjector : AndroidInjector<AppCompatActivity, AndroidScope<AppCompatActivity>> {
    override val kodeinScope: AndroidScope<AppCompatActivity> get() = androidActivityScope

    override fun initializeInjector() {
        val activityModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@AppCompatActivityInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<Activity>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<FragmentActivity>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<AppCompatActivity>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<FragmentManager>(erased()) with InstanceBinding(erased(), kodeinComponent.fragmentManager)
            Bind<LoaderManager>(erased()) with InstanceBinding(erased(), kodeinComponent.loaderManager)
            Bind<SupportFragmentManager>(erased()) with InstanceBinding(erased(), kodeinComponent.supportFragmentManager)
            Bind<SupportLoaderManager>(erased()) with InstanceBinding(erased(), kodeinComponent.supportLoaderManager)
            Bind<LayoutInflater>(erased(), tag =ACTIVITY_LAYOUT_INFLATER) with InstanceBinding(erased(), kodeinComponent.layoutInflater)

            import(provideOverridingModule(), allowOverride = true)
        }

        _inject(this, activityModule, kodeinComponent.appKodein())
    }
}

/**
 * A base class that manages an [AppCompatActivityInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinAppCompatActivity : AppCompatActivity(), AppCompatActivityInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()

        super.onCreate(savedInstanceState)
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

private fun Activity.mustBeAndroidInjector() {
    if(this !is AndroidInjector<*, *>) {
        throw RuntimeException("The Activity of a Kodein Fragment component must be one of " +
                "KodeinActivity, KodeinFragmentActivity, KodeinAppCompatActivity, " +
                "ActivityInjector, FragmentActivityInjector, or AppCompatActivityInjector")
    }
}

/**
 * An interface for adding injection and bindings to a Fragment.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@Fragment
 * - Context = Fragment.getActivity
 * - Activity = Fragment.getActivity
 * - Fragment = this@Fragment
 * - FragmentManager = Fragment.getFragmentManager
 * - LoaderManager = Fragment.getLoaderManager
 * - LayoutInflater = Fragment.getActivity.getLayoutInflater, Activity.getLayoutInflater
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein] and from Fragment.getActivity's [Kodein] if it implements [KodeinInjected].
 */
interface FragmentInjector : AndroidInjector<Fragment, AndroidScope<Fragment>> {
    override val kodeinScope: AndroidScope<Fragment> get() = androidFragmentScope

    override fun initializeInjector() {
        val activity = kodeinComponent.activity
        activity.mustBeAndroidInjector()

        val parentFragment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            kodeinComponent.parentFragment
        } else {
            null
        }
        parentFragment?.mustBeAndroidInjector()
        val hasParentFragment = parentFragment != null

        val fragmentModule = Kodein.Module {
            Bind<KodeinInjected>(erased(), overrides = true) with InstanceBinding(erased(), this@FragmentInjector)
            activity?.let {
                Bind<LayoutInflater>(erased(), tag = ACTIVITY_LAYOUT_INFLATER, overrides = true) with InstanceBinding(erased(), activity.layoutInflater)
            }
            Bind<Fragment>(erased(), overrides = hasParentFragment) with InstanceBinding(erased(), kodeinComponent)
            Bind<FragmentManager>(erased(), overrides = true) with InstanceBinding(erased(), kodeinComponent.fragmentManager)
            Bind<LoaderManager>(erased(), overrides = true) with InstanceBinding(erased(), kodeinComponent.loaderManager)

            import(provideOverridingModule(), allowOverride = true)
        }

        val parent = parentFragment ?: activity

        _inject(this, fragmentModule, (parent as KodeinInjected).injector.kodein().value)
    }
}

/**
 * A base class that manages a [FragmentInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinFragment : Fragment(), FragmentInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()

        super.onCreate(savedInstanceState)
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

/**
 * A base class that manages a [FragmentInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinPreferenceFragment : PreferenceFragment(), FragmentInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()

        super.onCreate(savedInstanceState)
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

private fun Fragment.mustBeAndroidInjector() {
    if(this !is AndroidInjector<*, *>) {
        throw RuntimeException("The parent Fragment of a Kodein Fragment component must be one of " +
                "KodeinFragment, KodeinSupportFragment, " +
                "FragmentInjector, or SupportFragmentInjector")
    }
}

/**
 * An interface for adding injection and bindings to a android.support.v4.app.Fragment.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@android.support.v4.app.Fragment
 * - Context = android.support.v4.app.Fragment.getActivity
 * - Activity = android.support.v4.app.Fragment.getActivity
 * - FragmentActivity = android.support.v4.app.Fragment.getActivity (if `activity` is a `FragmentActivity`)
 * - AppCompatActivity = android.support.v4.app.Fragment.getActivity (if `activity` is a `AppCompatActivity`)
 * - android.support.v4.app.Fragment = this@android.support.v4.app.Fragment
 * - FragmentManager = android.support.v4.app.Fragment.getFragmentManager
 * - LoaderManager = android.support.v4.app.Fragment.getLoaderManager
 * - LayoutInflater = android.support.v4.app.Fragment.getActivity.getLayoutInflater, Activity.getLayoutInflater
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein] and from android.support.v4.app.Fragment.getActivity's [Kodein] if it implements [KodeinInjected].
 */
interface SupportFragmentInjector : AndroidInjector<SupportFragment, AndroidScope<SupportFragment>> {
    override val kodeinScope: AndroidScope<SupportFragment> get() = androidSupportFragmentScope

    override fun initializeInjector() {
        val activity = kodeinComponent.activity
        activity.mustBeAndroidInjector()

        val parentFragment = kodeinComponent.parentFragment
        parentFragment?.mustBeAndroidInjector()
        val hasParentFragment = parentFragment != null

        val fragmentModule = Kodein.Module {
            Bind<KodeinInjected>(erased(), overrides = true) with InstanceBinding(erased(), this@SupportFragmentInjector)
            activity?.let {
                Bind<LayoutInflater>(erased(), ACTIVITY_LAYOUT_INFLATER, overrides = true) with InstanceBinding(erased(), activity.layoutInflater)
            }
            Bind<SupportFragment>(erased(), overrides = hasParentFragment) with InstanceBinding(erased(), kodeinComponent)
            Bind<SupportFragmentManager>(erased(), overrides = true) with InstanceBinding(erased(), kodeinComponent.fragmentManager)
            Bind<SupportLoaderManager>(erased(), overrides = true) with InstanceBinding(erased(), kodeinComponent.loaderManager)

            import(provideOverridingModule(), allowOverride = true)
        }

        val parent = parentFragment ?: activity

        _inject(this, fragmentModule, (parent as KodeinInjected).injector.kodein().value)
    }
}

/**
 * A base class that manages a [SupportFragmentInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinSupportFragment : SupportFragment(), SupportFragmentInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()

        super.onCreate(savedInstanceState)
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

private fun SupportFragment.mustBeAndroidInjector() {
    if(this !is AndroidInjector<*, *>) {
        throw RuntimeException("The parent Fragment of a Kodein Fragment component must be one of " +
                "KodeinFragment, KodeinSupportFragment, " +
                "FragmentInjector, or SupportFragmentInjector")
    }
}

/**
 * An interface for adding injection and bindings to a Service.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@Service
 * - Context = this@Service
 * - Service = this@Service
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface ServiceInjector : AndroidInjector<Service, AndroidScope<Service>> {
    override val kodeinScope: AndroidScope<Service> get() = androidServiceScope

    override fun initializeInjector() {
        val serviceModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@ServiceInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<Service>(erased()) with InstanceBinding(erased(), kodeinComponent)

            import(provideOverridingModule(), allowOverride = true)
        }

        _inject(this, serviceModule, kodeinComponent.appKodein())
    }
}

/**
 * A base class that manages a [ServiceInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinService : Service(), ServiceInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate() {
        super.onCreate()

        initializeInjector()
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

/**
 * An interface for adding injection and bindings to an IntentService.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@IntentService
 * - Context = this@IntentService
 * - Service = this@IntentService
 * - IntentService = this@IntentService
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface IntentServiceInjector : AndroidInjector<IntentService, AndroidScope<IntentService>> {
    override val kodeinScope: AndroidScope<IntentService> get() = androidServiceScope

    override fun initializeInjector() {
        val serviceModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@IntentServiceInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<Service>(erased()) with InstanceBinding(erased(), kodeinComponent)
            Bind<IntentService>(erased()) with InstanceBinding(erased(), kodeinComponent)

            import(provideOverridingModule(), allowOverride = true)
        }

        _inject(this, serviceModule, kodeinComponent.appKodein())
    }
}

/**
 * A base class that manages an [IntentServiceInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 *
 * @param name The name of the service
 */
abstract class KodeinIntentService(name: String) : IntentService(name), IntentServiceInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    /**
     * Default constructor that sets the service name to "KodeinIntentService".
     */
    constructor() : this("KodeinIntentService")

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    override fun onCreate() {
        super.onCreate()

        initializeInjector()
    }

    final override fun destroyInjector() = super.destroyInjector()

    /** @suppress */
    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}

/**
 * An interface for adding injection and bindings to a BroadcastReceiver.
 *
 * Implementations should set [context] to the `Context` from BroadcastReceiver.onReceive.
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = this@BroadcastReceiver
 * - Context = BroadcastReceiverInjector.context
 * - BroadcastReceiver = this@BroadcastReceiver
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface BroadcastReceiverInjector : AndroidInjector<BroadcastReceiver, AndroidScope<BroadcastReceiver>> {
    override val kodeinScope: AndroidScope<BroadcastReceiver> get() = androidBroadcastReceiverScope

    /**
     * Property that needs to be set in BroadcastReceiver.onReceive.
     */
    var context: Context?

    override fun initializeInjector() {
        val context = context ?: throw IllegalStateException("Please set the context property in onReceive")

        val receiverModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@BroadcastReceiverInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), context)
            Bind<BroadcastReceiver>(erased()) with InstanceBinding(erased(), kodeinComponent)

            import(provideOverridingModule(), allowOverride = true)
        }

        _inject(this, receiverModule, context.appKodein())
    }

    override fun destroyInjector(): ScopeRegistry? {
        context = null
        return super.destroyInjector()
    }
}

/**
 * A base class that manages a [BroadcastReceiverInjector] for easy bootstrapping of Kodein.
 * Injections will be available as soon as [onBroadcastReceived] is called and will be destroyed immediately after it returns.
 */
abstract class KodeinBroadcastReceiver : BroadcastReceiver(), BroadcastReceiverInjector {
    final override val injector = KodeinInjector()
    final override val kodeinComponent = super.kodeinComponent
    final override val kodeinScope = super.kodeinScope

    final override var context: Context? = null

    final override fun initializeInjector() = super.initializeInjector()

    /** @suppress */
    final override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        initializeInjector()
        onBroadcastReceived(context, intent)
        destroyInjector()
    }

    /**
     * Override this instead of onReceive to handle received broadcast intents.
     *
     * @param context The context.
     * @param intent The intent.
     */
    abstract fun onBroadcastReceived(context: Context, intent: Intent)

    final override fun destroyInjector() = super.destroyInjector()
}