package kodein.demo.coffee

import org.kodein.Kodein
import org.kodein.erased.bind
import org.kodein.erased.instance
import org.kodein.erased.singleton

class Thermosiphon(private val heater: Heater) : Pump {

    init {
        console.log("<Creating Thermosiphon>")
    }

    override fun pumpWater() {
        if (heater.isHot)
            console.log("=> => pumping => =>")
        else
            console.log("!!! water is cold !!!")
    }
}

val thermosiphonModule = Kodein.Module {
    bind<Pump>() with singleton { Thermosiphon(instance()) }
}
