package kodein.demo.coffee

import kodein.demo.Logger

public interface Ration {
    fun name(): String
}

public class Coffee(private val log: Logger) : Ration {
    init { log.log("<Creating CoffeeRation>") }
    override fun name(): String = "coffee"
}

public class Tea(private val log: Logger) : Ration {
    init { log.log("<Creating TeaRation>") }
    override fun name(): String = "tea"
}

public class Kettle<T : Ration>(
        private val log: Logger,
        private val heater: Heater,
        private val pump: Pump,
        private val ration: () -> T // We will need a new ration every time
) {

    init {
        log.log("<Creating CoffeeMaker>")
    }

    public fun brew() {
        heater.on();
        pump.pumpWater();
        val ration = ration()
        log.log("[_]P ${ration.name()} ${System.identityHashCode(ration)} [_]P");
        heater.off();
    }
}
