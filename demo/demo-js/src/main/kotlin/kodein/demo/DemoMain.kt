package kodein.demo

import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kodein.demo.coffee.electricHeaterModule
import kodein.demo.coffee.thermosiphonModule
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

        bind<Coffee>() with provider { Coffee() }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<*>>() with singleton { Kettle<Coffee>(instance(), instance(), provider()) }

        constant("author") with "Salomon BRYS"
    }

    private val _kettle: Kettle<Coffee> by instance()

    init {
        val author: String by instance("author")
        console.log("Kodein 5 Demo by $author")

        _kettle.brew()
    }

}
