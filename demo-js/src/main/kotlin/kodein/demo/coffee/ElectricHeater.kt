package kodein.demo.coffee

import org.kodein.Kodein
import org.kodein.erased.bind
import org.kodein.erased.singleton

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

val electricHeaterModule = Kodein.Module {
    bind<Heater>() with singleton { ElectricHeater() }
}
