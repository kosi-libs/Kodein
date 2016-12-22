package com.github.salomonbrys.kodein.android

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.github.salomonbrys.kodein.*
import android.support.v4.app.Fragment as SupportFragment
import android.support.v4.app.FragmentManager as SupportFragmentManager
import android.support.v4.app.LoaderManager as SupportLoaderManager

private fun inject(injector: KodeinInjected, componentModule: Kodein.Module, superKodein: Kodein) {
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
   * Adds bindings specific for this component and injects all the properties created with the [injector].
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
  fun provideOverridingBindings(): Kodein.Builder.() -> Unit = {}
}

/**
 * An interface for adding injection and bindings to an [Activity].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [Activity.this]
 * - [Context] = [Activity.this]
 * - [Activity] = [Activity.this]
 * - [FragmentManager] = [Activity.getFragmentManager]
 * - [LoaderManager] = [Activity.getLoaderManager]
 * - [LayoutInflater] = [Activity.getLayoutInflater]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface ActivityInjector : AndroidInjector<Activity, AndroidScope<Activity>> {
  override val kodeinScope: AndroidScope<Activity> get() = androidActivityScope

  override fun initializeInjector() {
    val activityModule = Kodein.Module {
      bindErased<KodeinInjected>() with erasedInstance(this@ActivityInjector)
      bindErased<Context>() with erasedInstance(kodeinComponent)
      bindErased<Activity>() with erasedInstance(kodeinComponent)
      bindErased<FragmentManager>() with erasedInstance(kodeinComponent.fragmentManager)
      bindErased<LoaderManager>() with erasedInstance(kodeinComponent.loaderManager)
      bindErased<LayoutInflater>() with erasedInstance(kodeinComponent.layoutInflater)

      provideOverridingBindings()(this)
    }

    inject(this, activityModule, kodeinComponent.appKodein())
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to a [FragmentActivity].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [FragmentActivity.this]
 * - [Context] = [FragmentActivity.this]
 * - [Activity] = [FragmentActivity.this]
 * - [FragmentActivity] = [FragmentActivity.this]
 * - [FragmentManager] = [FragmentActivity.getFragmentManager]
 * - [LoaderManager] = [FragmentActivity.getLoaderManager]
 * - [LayoutInflater] = [FragmentActivity.getLayoutInflater]
 * - [android.support.v4.app.FragmentManager] = [FragmentActivity.getSupportFragmentManager]
 * - [android.support.v4.app.LoaderManager] = [FragmentActivity.getSupportLoaderManager]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface FragmentActivityInjector : AndroidInjector<FragmentActivity, AndroidScope<FragmentActivity>> {
  override val kodeinScope: AndroidScope<FragmentActivity> get() = androidActivityScope

  override fun initializeInjector() {
    val activityModule = Kodein.Module {
      bindErased<KodeinInjected>() with erasedInstance(this@FragmentActivityInjector)
      bindErased<Context>() with erasedInstance(kodeinComponent)
      bindErased<Activity>() with erasedInstance(kodeinComponent)
      bindErased<FragmentActivity>() with erasedInstance(kodeinComponent)
      bindErased<FragmentManager>() with erasedInstance(kodeinComponent.fragmentManager)
      bindErased<LoaderManager>() with erasedInstance(kodeinComponent.loaderManager)
      bindErased<SupportFragmentManager>() with erasedInstance(kodeinComponent.supportFragmentManager)
      bindErased<SupportLoaderManager>() with erasedInstance(kodeinComponent.supportLoaderManager)
      bindErased<LayoutInflater>() with erasedInstance(kodeinComponent.layoutInflater)

      provideOverridingBindings()(this)
    }

    inject(this, activityModule, kodeinComponent.appKodein())
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to an [AppCompatActivity].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [AppCompatActivity.this]
 * - [Context] = [AppCompatActivity.this]
 * - [Activity] = [AppCompatActivity.this]
 * - [FragmentActivity] = [AppCompatActivity.this]
 * - [AppCompatActivity] = [AppCompatActivity.this]
 * - [FragmentManager] = [AppCompatActivity.getFragmentManager]
 * - [LoaderManager] = [AppCompatActivity.getLoaderManager]
 * - [LayoutInflater] = [AppCompatActivity.getLayoutInflater]
 * - [android.support.v4.app.FragmentManager] = [AppCompatActivity.getSupportFragmentManager]
 * - [android.support.v4.app.LoaderManager] = [AppCompatActivity.getSupportLoaderManager]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface AppCompatActivityInjector : AndroidInjector<AppCompatActivity, AndroidScope<AppCompatActivity>> {
  override val kodeinScope: AndroidScope<AppCompatActivity> get() = androidActivityScope

  override fun initializeInjector() {
    val activityModule = Kodein.Module {
      bindErased<KodeinInjected>() with erasedInstance(this@AppCompatActivityInjector)
      bindErased<Context>() with erasedInstance(kodeinComponent)
      bindErased<Activity>() with erasedInstance(kodeinComponent)
      bindErased<FragmentActivity>() with erasedInstance(kodeinComponent)
      bindErased<AppCompatActivity>() with erasedInstance(kodeinComponent)
      bindErased<FragmentManager>() with erasedInstance(kodeinComponent.fragmentManager)
      bindErased<LoaderManager>() with erasedInstance(kodeinComponent.loaderManager)
      bindErased<SupportFragmentManager>() with erasedInstance(kodeinComponent.supportFragmentManager)
      bindErased<SupportLoaderManager>() with erasedInstance(kodeinComponent.supportLoaderManager)
      bindErased<LayoutInflater>() with erasedInstance(kodeinComponent.layoutInflater)

      provideOverridingBindings()(this)
    }

    inject(this, activityModule, kodeinComponent.appKodein())
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to a [Fragment].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [Fragment.this]
 * - [Context] = [Fragment.getActivity]
 * - [Activity] = [Fragment.getActivity]
 * - [Fragment] = [Fragment.this]
 * - [FragmentManager] = [Fragment.getFragmentManager]
 * - [LoaderManager] = [Fragment.getLoaderManager]
 * - [LayoutInflater] = [Fragment.getActivity.getLayoutInflater][Activity.getLayoutInflater]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein] and from [Fragment.getActivity]'s [Kodein] if it implements [KodeinInjected].
 */
interface FragmentInjector : AndroidInjector<Fragment, AndroidScope<Fragment>> {
  override val kodeinScope: AndroidScope<Fragment> get() = androidFragmentScope

  override fun initializeInjector() {
    val activity = kodeinComponent.activity

    val fragmentModule = Kodein.Module {
      bindErased<KodeinInjected>(overrides = true) with erasedInstance(this@FragmentInjector)
      activity?.let {
        when(it) {
          !is KodeinActivity -> {
            bindErased<Context>(overrides = true) with erasedInstance(it)
            bindErased<Activity>(overrides = true) with erasedInstance(it)
          }
        }

        bindErased<LayoutInflater>(overrides = true) with erasedInstance(activity.layoutInflater)
      }
      bindErased<Fragment>() with erasedInstance(kodeinComponent)
      bindErased<FragmentManager>(overrides = true) with erasedInstance(kodeinComponent.fragmentManager)
      bindErased<LoaderManager>(overrides = true) with erasedInstance(kodeinComponent.loaderManager)

      provideOverridingBindings()(this)
    }

    val superKodeins = if(activity is KodeinInjected) {
      activity.injector.kodein().value
    }
    else {
      kodeinComponent.appKodein()
    }

    inject(this, fragmentModule, superKodeins)
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to a [android.support.v4.app.Fragment].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [android.support.v4.app.Fragment.this]
 * - [Context] = [android.support.v4.app.Fragment.getActivity]
 * - [Activity] = [android.support.v4.app.Fragment.getActivity]
 * - [FragmentActivity] = [android.support.v4.app.Fragment.getActivity] (if `activity` is a `FragmentActivity`)
 * - [AppCompatActivity] = [android.support.v4.app.Fragment.getActivity] (if `activity` is a `AppCompatActivity`)
 * - [android.support.v4.app.Fragment] = [android.support.v4.app.Fragment.this]
 * - [FragmentManager] = [android.support.v4.app.Fragment.getFragmentManager]
 * - [LoaderManager] = [android.support.v4.app.Fragment.getLoaderManager]
 * - [LayoutInflater] = [android.support.v4.app.Fragment.getActivity.getLayoutInflater][Activity.getLayoutInflater]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein] and from [android.support.v4.app.Fragment.getActivity]'s [Kodein] if it implements [KodeinInjected].
 */
interface SupportFragmentInjector : AndroidInjector<SupportFragment, AndroidScope<SupportFragment>> {
  override val kodeinScope: AndroidScope<SupportFragment> get() = androidSupportFragmentScope

  override fun initializeInjector() {
    val activity = kodeinComponent.activity

    val fragmentModule = Kodein.Module {
      bindErased<KodeinInjected>(overrides = true) with erasedInstance(this@SupportFragmentInjector)
      activity?.let {
        when(it) {
          !is KodeinActivity, !is KodeinFragmentActivity, !is KodeinAppCompatActivity -> {
            bindErased<Context>(overrides = true) with erasedInstance(it)
            bindErased<Activity>(overrides = true) with erasedInstance(it)
            if(it is FragmentActivity) {
              bindErased<FragmentActivity>(overrides = true) with erasedInstance(it)
              if(it is AppCompatActivity) bindErased<AppCompatActivity>(overrides = true) with erasedInstance(it)
            }
          }
        }

        bindErased<LayoutInflater>(overrides = true) with erasedInstance(activity.layoutInflater)
      }
      bindErased<SupportFragment>() with erasedInstance(kodeinComponent)
      bindErased<SupportFragmentManager>(overrides = true) with erasedInstance(kodeinComponent.fragmentManager)
      bindErased<SupportLoaderManager>(overrides = true) with erasedInstance(kodeinComponent.loaderManager)

      provideOverridingBindings()(this)
    }

    val superKodeins = if(activity is KodeinInjected) {
      activity.injector.kodein().value
    }
    else {
      kodeinComponent.appKodein()
    }

    inject(this, fragmentModule, superKodeins)
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to a [Service].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [Service.this]
 * - [Context] = [Service.this]
 * - [Service] = [Service.this]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface ServiceInjector : AndroidInjector<Service, AndroidScope<Service>> {
  override val kodeinScope: AndroidScope<Service> get() = androidServiceScope

  override fun initializeInjector() {
    val serviceModule = Kodein.Module {
      bindErased<KodeinInjected>() with erasedInstance(this@ServiceInjector)
      bindErased<Context>() with erasedInstance(kodeinComponent)
      bindErased<Service>() with erasedInstance(kodeinComponent)

      provideOverridingBindings()(this)
    }

    inject(this, serviceModule, kodeinComponent.appKodein())
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

  override fun onCreate() {
    super.onCreate()

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to an [IntentService].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [IntentService.this]
 * - [Context] = [IntentService.this]
 * - [Service] = [IntentService.this]
 * - [IntentService] = [IntentService.this]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface IntentServiceInjector : AndroidInjector<IntentService, AndroidScope<IntentService>> {
  override val kodeinScope: AndroidScope<IntentService> get() = androidServiceScope

  override fun initializeInjector() {
    val serviceModule = Kodein.Module {
      bindErased<KodeinInjected>() with erasedInstance(this@IntentServiceInjector)
      bindErased<Context>() with erasedInstance(kodeinComponent)
      bindErased<Service>() with erasedInstance(kodeinComponent)
      bindErased<IntentService>() with erasedInstance(kodeinComponent)

      provideOverridingBindings()(this)
    }

    inject(this, serviceModule, kodeinComponent.appKodein())
  }
}

/**
 * A base class that manages an [IntentServiceInjector] for easy bootstrapping of Kodein.
 * Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.
 */
abstract class KodeinIntentService(name: String) : IntentService(name), IntentServiceInjector {
  final override val injector = KodeinInjector()
  final override val kodeinComponent = super.kodeinComponent
  final override val kodeinScope = super.kodeinScope

  constructor() : this("KodeinIntentService")

  final override fun initializeInjector() = super.initializeInjector()

  override fun onCreate() {
    super.onCreate()

    initializeInjector()
  }

  final override fun destroyInjector() = super.destroyInjector()

  override fun onDestroy() {
    super.onDestroy()

    destroyInjector()
  }
}

/**
 * An interface for adding injection and bindings to a [BroadcastReceiver].
 *
 * Implementations should set [context] to the `Context` from [BroadcastReceiver.onReceive].
 *
 * The following bindings are provided:
 *
 * - [KodeinInjected] = [BroadcastReceiver.this]
 * - [Context] = [BroadcastReceiverInjector.context]
 * - [BroadcastReceiver] = [BroadcastReceiver.this]
 *
 * The underlying [Kodein] object will [Kodein.Builder.extend] from [appKodein].
 */
interface BroadcastReceiverInjector : AndroidInjector<BroadcastReceiver, AndroidScope<BroadcastReceiver>> {
  override val kodeinScope: AndroidScope<BroadcastReceiver> get() = androidBroadcastReceiverScope
  var context: Context?

  override fun initializeInjector() {
    val receiverModule = Kodein.Module {
      bindErased<KodeinInjected>() with erasedInstance(this@BroadcastReceiverInjector)
      bindErased<Context>() with erasedInstance(context!!)
      bindErased<BroadcastReceiver>() with erasedInstance(kodeinComponent)

      provideOverridingBindings()(this)
    }

    inject(this, receiverModule, context!!.appKodein())
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

  final override fun onReceive(context: Context, intent: Intent) {
    this.context = context
    initializeInjector()
    onBroadcastReceived(context, intent)
    destroyInjector()
  }

  abstract fun onBroadcastReceived(context: Context, intent: Intent)

  final override fun destroyInjector() = super.destroyInjector()
}