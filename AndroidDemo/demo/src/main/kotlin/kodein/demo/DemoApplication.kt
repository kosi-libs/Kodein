package kodein.demo

import android.app.Application
import com.github.salomonbrys.kodein.*
import kodein.demo.coffee.CoffeeMaker
import kodein.demo.coffee.CoffeeRation
import kodein.demo.coffee.bindElectricHeater
import kodein.demo.coffee.bindThermosiphon

public class DemoApplication : Application(), KodeinHolder {

    override val kodein: Kodein by lazyKodein {
        bindThermosiphon()
        bindElectricHeater()

        bind<CoffeeRation>() with { CoffeeRation(it.instance()) }

        bind<Logger>() with instance(Logger())

        bind<CoffeeMaker>() with singleton { CoffeeMaker(it.instance(), it.instance(), it.instance(), it.provider()) }

        bindFooBar()
    }


}
