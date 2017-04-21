package kodein.demo

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.android.autoAndroidModule
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kodein.demo.coffee.electricHeaterModule
import kodein.demo.coffee.thermosiphonModule

class DemoApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@DemoApplication))

        bind<Logger>() with instance(Logger())

        import(thermosiphonModule)
        import(electricHeaterModule)

        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<Coffee>>() with scopedSingleton(androidActivityScope) { Kettle<Coffee>(instance(), instance(), instance(), provider()) }

        bind<String>() with instance("DemoApplication")
    }

}
