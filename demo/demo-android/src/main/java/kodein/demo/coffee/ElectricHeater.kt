package kodein.demo.coffee

import org.kodein.Kodein
import org.kodein.bind
import org.kodein.instance
import org.kodein.singleton
import kodein.demo.Logger

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

val electricHeaterModule = Kodein.Module {
    bind<Heater>() with singleton { ElectricHeater(instance()) }
}
