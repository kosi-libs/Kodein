package kodein.demo

import android.app.Application
import com.github.salomonbrys.kodein.*
import kodein.demo.coffee.*

class DemoApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Logger>() with instance(Logger())

        import(thermosiphonModule)
        import(electricHeaterModule)

        bind<Coffee>() with provider { Coffee(instance()) }
        bind<Kettle<Coffee>>() with singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }
    }

}
