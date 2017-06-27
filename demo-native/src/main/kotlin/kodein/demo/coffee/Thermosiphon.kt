package kodein.demo.coffee

//import com.github.salomonbrys.kodein.Kodein
//import com.github.salomonbrys.kodein.erased.bind
//import com.github.salomonbrys.kodein.erased.instance
//import com.github.salomonbrys.kodein.erased.singleton

class Thermosiphon(private val heater: Heater) : Pump {

    init {
        println("<Creating Thermosiphon>")
    }

    override fun pumpWater() {
        if (heater.isHot)
            println("=> => pumping => =>")
        else
            println("!!! water is cold !!!")
    }
}

//val thermosiphonModule = Kodein.Module {
//    bind<Pump>() with singleton { Thermosiphon(instance()) }
//}
