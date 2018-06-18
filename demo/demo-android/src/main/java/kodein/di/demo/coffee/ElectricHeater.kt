package kodein.di.demo.coffee

import org.kodein.di.Kodein
import kodein.di.demo.Logger
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ElectricHeater(private val log: Logger) : Heater {
    private var heating: Boolean = false

    init {
        log.log("<Creating ElectricHeater>")
    }

    override fun on() {
        log.log("~ ~ ~ heating ~ ~ ~")
        this.heating = true
    }

    override fun off() {
        log.log(". . . cooling . . .")
        this.heating = false
    }

    override val isHot: Boolean get() = heating
}

val electricHeaterModule = Kodein.Module("Electric Heater") {
    bind<Heater>() with singleton { ElectricHeater(instance()) }
}
