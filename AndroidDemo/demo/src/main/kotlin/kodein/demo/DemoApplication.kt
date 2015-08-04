package kodein.demo

import android.app.Application
import com.github.salomonbrys.kodein.*
import kodein.demo.coffee.*

public class DemoApplication : Application(), KodeinHolder {

    override val kodein: Kodein by lazyKodein {

        bind<Logger>() with instance(Logger())

        bindThermosiphon()
        bindElectricHeater()

        bind<Coffee>() with { Coffee(it.instance()) }
        bind<Kettle<Coffee>>() with singleton { Kettle<Coffee>(it.instance(), it.instance(), it.instance(), it.provider()) }
    }


}
