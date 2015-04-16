package kodein.demo.coffee

import kodein.demo.Logger

public class CoffeeRation(private val log: Logger) {

    init {
        log.log("<Creating CoffeeRation>")
    }

}

