package kodein.demo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kodein.demo.coffee.thermosiphonModule
import org.kodein.*
import org.kodein.android.appKodein
import org.kodein.android.extend

class MainActivity : Activity(), KodeinAware {

    override val kodeinContext: KodeinContext<*> = kcontext(this)

    override val kodein: Kodein by Kodein.Lazy {
        extend(appKodein)
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
        Log.i("Kodein", kodein.container.bindings.description())
        Log.i("Kodein", "====================================================")
    }

}
