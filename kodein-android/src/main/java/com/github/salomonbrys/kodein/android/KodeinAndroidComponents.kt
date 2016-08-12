package com.github.salomonbrys.kodein.android

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

interface AndroidInjector<T, out S : AndroidScope<T>> : KodeinInjected {
  // this covers most of the use cases (and it really shouldn't ever be in a position where the cast doesn't work)
  @Suppress("UNCHECKED_CAST")
  val kodeinComponent: T get() = this as T
  val kodeinScope: S

  fun initializeInjector()

  fun destroyInjector() = kodeinScope.removeFromScope(kodeinComponent)

  fun provideOverridingBindings(): Kodein.Builder.() -> Unit = {}
}

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

abstract class KodeinBroadcastReceiver : BroadcastReceiver(), BroadcastReceiverInjector {
  final override val injector = KodeinInjector()
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