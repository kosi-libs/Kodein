package kodein.demo

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.erased.*
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kodein.demo.coffee.electricHeaterModule
import kodein.demo.coffee.thermosiphonModule

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

    private val _kettle: Kettle<Coffee> = instance()

    init {
        val author: String = kodein.instance("author")
        console.log("Demo by $author")

        _kettle.brew()
    }

}
