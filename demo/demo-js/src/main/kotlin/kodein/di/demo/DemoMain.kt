package kodein.di.demo

import kodein.di.demo.coffee.Coffee
import kodein.di.demo.coffee.Kettle
import kodein.di.demo.coffee.electricHeaterModule
import kodein.di.demo.coffee.thermosiphonModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.*

fun main(args: Array<String>) {
    Application()
}

class Application : KodeinAware {

    override val kodein = Kodein {
        import(thermosiphonModule)
        import(electricHeaterModule)

        bind() from instance(JsLogger())
        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<*>>() with singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }

        constant("author") with "Salomon BRYS"
    }

    private val _logger: CommonLogger by instance()
    private val _kettle: Kettle<Coffee> by instance()

    init {
        val author: String by instance("author")
        _logger.log("Kodein 5 Demo by $author")

        _kettle.brew()
    }

}

class JsLogger : CommonLogger {
    override fun log(msg: String) {
        console.log(msg)
    }

}