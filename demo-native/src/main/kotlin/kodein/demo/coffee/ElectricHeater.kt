package kodein.demo.coffee

//import com.github.salomonbrys.kodein.Kodein
//import com.github.salomonbrys.kodein.erased.bind
//import com.github.salomonbrys.kodein.erased.singleton

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

//val electricHeaterModule = Kodein.Module {
//    bind<Heater>() with singleton { ElectricHeater() }
//}
