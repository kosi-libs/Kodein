package kodein.demo

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.FragmentInjector
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle

// by implementing FragmentInjector we get the same behavior as KodeinFragment, but we have the flexibility to subclass whatever we want
// All we need to do is override injector with a simple KodeinInjector() and call initializeInjector and destroyInjector when appropriate
class MainFragment : Fragment(), FragmentInjector {
    override val injector: KodeinInjector = KodeinInjector()

    // will be the same instance as the coffeeMaker in MainActivity
    lateinit var coffeeMaker: Kettle<Coffee>

    val log: Logger by instance()
    val logTag: String by instance()

    val textView: TextView by lazy { activity.findViewById<TextView>(R.id.text) }

    override fun provideOverridingModule() = Kodein.Module {
        bind<String>(overrides = true) with instance("MainFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeInjector()

        Log.i(logTag, "onCreate")

        // since Kettle<Coffee> is bound in the activity scope we need a reference to an Activity to retrieve it
        coffeeMaker = injector.kodein().value.instance(arg = activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log.callback = {
            textView.text = log.text
        }

        log.log("Starting to brew coffee using $coffeeMaker - MainFragment")

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 3000)

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 6000)
    }

    override fun onDestroy() {
        super.onDestroy()

        destroyInjector()
    }
}
