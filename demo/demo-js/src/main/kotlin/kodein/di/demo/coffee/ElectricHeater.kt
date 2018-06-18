package kodein.di.demo.coffee

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

class ElectricHeater : Heater {
    private var heating: Boolean = false

    init {
        console.log("<Creating ElectricHeater>")
    }

    override fun on() {
        console.log("~ ~ ~ heating ~ ~ ~")
        this.heating = true
    }

    override fun off() {
        console.log(". . . cooling . . .")
        this.heating = false
    }

    override val isHot: Boolean get() = heating
}

val electricHeaterModule = Kodein.Module("Electric Heater") {
    bind<Heater>() with singleton { ElectricHeater() }
}
