package kodein.demo

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.KodeinApplication
import kodein.demo.coffee.*

public class DemoApplication : Application(), KodeinApplication {

    override val kodein = Kodein {
        bind<Logger>() with instance(Logger())

        import(thermosiphonModule)
        import(electricHeaterModule)

        bind<Coffee>() with provider { Coffee(instance()) }
        bind<Kettle<Coffee>>() with singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }
    }

}
