package kodein.demo.coffee

import org.kodein.Kodein
import kodein.demo.Logger
import org.kodein.generic.bind
import org.kodein.generic.instance
import org.kodein.generic.singleton

class Thermosiphon(private val log: Logger, private val heater: Heater) : Pump {

    init {
        log.log("<Creating Thermosiphon>")
    }

    override fun pumpWater() {
        if (heater.isHot)
            log.log("=> => pumping => =>")
        else
            log.log("!!! water is cold !!!")
    }
}

val thermosiphonModule = Kodein.Module {
    bind<Pump>() with singleton { Thermosiphon(instance(), instance()) }
}
