package kodein.di.demo.coffee


interface Ration {
    fun name(): String
}

class Coffee : Ration {
    init { console.log("<Creating CoffeeRation>") }
    override fun name(): String = "coffee"
}

class Tea : Ration {
    init { console.log("<Creating TeaRation>") }
    override fun name(): String = "tea"
}

class Kettle<T : Ration>(
        private val heater: Heater,
        private val pump: Pump,
        private val ration: () -> T // We will need a new ration every time
) {

    init {
        console.log("<Creating CoffeeMaker>")
    }

    fun brew() {
        heater.on()
        pump.pumpWater()
        val ration = ration()

        console.log("[_]P ${ration.name()} [_]P")
        heater.off()
    }
}
