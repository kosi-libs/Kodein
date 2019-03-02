package kodein.di.demo

import org.kodein.di.*
import org.kodein.di.erased.*
import kodein.di.demo.coffee.*

fun main() {
    Application()
}

class Application : KodeinAware {

    override val kodein = Kodein {
        import(thermosiphonModule)
        import(electricHeaterModule)

        bind<CommonLogger>() with provider { ConsoleLogger() }
        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<*>>() with singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }

        constant("author") with "Salomon BRYS"
    }

    private val _kettle: Kettle<Coffee> by instance()
    private val _logger: CommonLogger by instance()

    init {
        val author: String by instance("author")
        _logger.log("Kodein 5 Demo by $author")

        _kettle.brew()
    }
}

class ConsoleLogger : CommonLogger {
    override fun log(s: String) {
        println(s)
    }
}