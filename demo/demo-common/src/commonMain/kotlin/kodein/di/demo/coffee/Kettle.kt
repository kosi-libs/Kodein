package kodein.di.demo.coffee

import kodein.di.demo.CommonLogger

abstract class Ration(val logger: CommonLogger) {
    abstract fun name(): String
}

class Coffee(logger: CommonLogger) : Ration(logger) {
    init { logger.log("<Creating CoffeeRation>") }
    override fun name(): String = "coffee"
}

class Tea(logger: CommonLogger) : Ration(logger) {
    init { logger.log("<Creating TeaRation>") }
    override fun name(): String = "tea"
}

class Kettle<T : Ration>(
        private val logger: CommonLogger,
        private val heater: Heater,
        private val pump: Pump,
        private val ration: () -> T // We will need a new ration every time
)   {

    init {
        logger.log("<Creating CoffeeMaker>")
    }

    fun brew() {
        heater.on()
        pump.pumpWater()
        val ration = ration()

        logger.log("[_]P ${ration.name()} [_]P")
        heater.off()
    }
}
