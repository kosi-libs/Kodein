package kodein.demo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.KodeinActivity
import com.github.salomonbrys.kodein.android.appKodein
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle

// since we extend KodeinActivity we have an injector that will automatically inject values when we're in onCreate
// see MainFragment for an example of how to do this using an interface
class MainActivity : KodeinActivity() {
    // will be the same instance as the coffeeMaker in MainFragment
    val coffeeMaker: Kettle<Coffee> by with(this as Activity).instance()
    val log: Logger by instance()
    val logTag: String by instance()

    override fun provideOverridingModule() = Kodein.Module {
        bind<MainActivity>() to instance(this)
        bind<String>(overrides = true) with instance("MainActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(logTag, "onCreate")

        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            log.log("Going to brew coffee using $coffeeMaker - MainActivity")

            fragmentManager.beginTransaction().add(R.id.fragment, MainFragment()).commit()
        }

        Log.i("Kodein", "=====================-BINDINGS-=====================")
        Log.i("Kodein", appKodein().container.bindings.description)
        Log.i("Kodein", "=====================----------=====================")
    }

}
