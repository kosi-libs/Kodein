package kodein.di.demo

import android.app.Activity
import android.app.Application
import kodein.di.demo.coffee.Coffee
import kodein.di.demo.coffee.Kettle
import kodein.di.demo.coffee.electricHeaterModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.WeakContextScope
import org.kodein.di.generic.*

class DemoApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@DemoApplication))

        bind<AndroidLogger>() with singleton { AndroidLogger() }

        import(electricHeaterModule)

        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<Coffee>>() with scoped(WeakContextScope.of<Activity>()).singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }
    }

    override fun onCreate() {
        super.onCreate()
        val k = kodein
        println(k)
    }

}
