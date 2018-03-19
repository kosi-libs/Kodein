package kodein.demo.coffee

import org.kodein.Kodein
import org.kodein.erased.bind
import org.kodein.erased.singleton
import org.kodein.erased.provider

class ElectricHeater : Heater {
    private var heating: Boolean = false

    init {
        println("<Creating ElectricHeater>")
    }

    override fun on() {
        println("~ ~ ~ heating ~ ~ ~")
        this.heating = true
    }

    override fun off() {
        println(". . . cooling . . .")
        this.heating = false
    }

    override val isHot: Boolean get() = heating
}

val electricHeaterModule = Kodein.Module {
    bind<Heater>() with provider { ElectricHeater() }
}
