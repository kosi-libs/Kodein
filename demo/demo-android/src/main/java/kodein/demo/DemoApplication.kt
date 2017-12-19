package kodein.demo

import android.app.Application
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kodein.demo.coffee.electricHeaterModule
import kodein.demo.coffee.thermosiphonModule
import org.kodein.*
import org.kodein.android.ActivityScope
import org.kodein.android.androidModule

class DemoApplication : Application(), KodeinAware {

    override val kodein = Kodein {
        import(androidModule(this@DemoApplication))

        bind<Logger>() with instance(Logger())

        import(electricHeaterModule)

        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<Coffee>>() with scoped(ActivityScope).singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }
    }

}
