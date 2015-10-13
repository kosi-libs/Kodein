package kodein.demo.coffee

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.singleton
import kodein.demo.Logger

public class ElectricHeater(private val log: Logger) : Heater {
    private var heating: Boolean = false

    init {
        log.log("<Creating ElectricHeater>")
    }

    override fun on() {
        log.log("~ ~ ~ heating ~ ~ ~");
        this.heating = true;
    }

    override fun off() {
        log.log(". . . cooling . . .");
        this.heating = false;
    }

    override val isHot: Boolean get() = heating;
}

public val electricHeaterModule = Kodein.Module {
    bind<Heater>() with singleton { ElectricHeater(instance()) }
}
