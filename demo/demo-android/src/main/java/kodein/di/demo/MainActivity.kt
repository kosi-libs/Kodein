package kodein.di.demo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kodein.di.demo.coffee.Coffee
import kodein.di.demo.coffee.Kettle
import kodein.di.demo.coffee.thermosiphonModule
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainActivity : Activity(), KodeinAware {

    override val kodeinContext: KodeinContext<*> = kcontext(this)

    private val _parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein, copy = Copy.All)
        import(thermosiphonModule)
    }

    // will be the same instance as the coffeeMaker in MainFragment
    val coffeeMaker: Kettle<Coffee> by instance()
    val log: Logger by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            log.log("Going to brew coffee using $coffeeMaker")

            fragmentManager.beginTransaction().add(R.id.fragment, MainFragment()).commit()
        }

        Log.i("Kodein", "=====================-BINDINGS-=====================")
        Log.i("Kodein", kodein.container.tree.bindings.description())
        Log.i("Kodein", "====================================================")
    }

}
