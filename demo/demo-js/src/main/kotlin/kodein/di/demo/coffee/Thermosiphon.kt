package kodein.di.demo.coffee

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

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

val thermosiphonModule = Kodein.Module("Thermosiphon") {
    bind<Pump>() with singleton { Thermosiphon(instance()) }
}
