package kodein.demo.coffee

import kodein.demo.Logger

public class CoffeeMaker(
        private val log: Logger,
        private val heater: Heater,
        private val pump: Pump,
        private val ration: () -> CoffeeRation // We will need a new coffee ration every time
) {

    init {
        log.log("<Creating CoffeeMaker>")
    }

    public fun brew() {
        heater.on();
        pump.pumpWater();
        val coffee = ration()
        log.log("[_]P coffee ${System.identityHashCode(coffee)} [_]P");
        heater.off();
    }
}
