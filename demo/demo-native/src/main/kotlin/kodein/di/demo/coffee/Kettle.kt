package kodein.di.demo.coffee


interface Ration {
    fun name(): String
}

class Coffee : Ration {
    init { println("<Creating CoffeeRation>") }
    override fun name(): String = "coffee"
}

class Tea : Ration {
    init { println("<Creating TeaRation>") }
    override fun name(): String = "tea"
}

class Kettle<T : Ration>(
        private val heater: Heater,
        private val pump: Pump,
        private val ration: () -> T // We will need a new ration every time
) {

    init {
        println("<Creating CoffeeMaker>")
    }

    fun brew() {
        heater.on()
        pump.pumpWater()
        val ration = ration()

        println("[_]P ${ration.name()} [_]P")
        heater.off()
    }
}
