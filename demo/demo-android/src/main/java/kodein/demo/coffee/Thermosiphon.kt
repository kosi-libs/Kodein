package kodein.demo.coffee

import org.kodein.Kodein
import org.kodein.bind
import org.kodein.instance
import org.kodein.singleton
import kodein.demo.Logger

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
