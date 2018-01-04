package kodein.demo

import android.app.Activity
import android.app.Application
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kodein.demo.coffee.electricHeaterModule
import org.kodein.*
import org.kodein.android.androidModule
import org.kodein.android.androidScope

class DemoApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidModule(this@DemoApplication))

        bind<Logger>() with instance(Logger())

        import(electricHeaterModule)

        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<Coffee>>() with scoped(androidScope<Activity>()).singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }
    }

}
