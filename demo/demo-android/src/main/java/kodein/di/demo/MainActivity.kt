package kodein.di.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kodein.di.demo.coffee.Coffee
import kodein.di.demo.coffee.Kettle
import kodein.di.demo.coffee.thermosiphonModule
import org.kodein.di.Copy
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedSubKodein
import org.kodein.di.description
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodeinContext: KodeinContext<*> = kcontext(this)

    override val kodein by retainedSubKodein(kodein(), copy = Copy.All) {
        import(thermosiphonModule)
    }

    // will be the same instance as the coffeeMaker in MainFragment
    val coffeeMaker: Kettle<Coffee> by instance()
    val log: AndroidLogger by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            log.log("Going to brew coffee using $coffeeMaker")

            supportFragmentManager.beginTransaction().add(R.id.fragment, MainFragment()).commit()
        }

        Log.i("Kodein", "=====================-BINDINGS-=====================")
        Log.i("Kodein", kodein.container.tree.bindings.description())
        Log.i("Kodein", "====================================================")
    }

}
