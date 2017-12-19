package kodein.demo

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import org.kodein.*
import org.kodein.android.activityKodein
import org.kodein.android.appKodein
import org.kodein.android.extend

// by implementing FragmentInjector we get the same behavior as KodeinFragment, but we have the flexibility to subclass whatever we want
// All we need to do is override injector with a simple KodeinInjector() and call initializeInjector and destroyInjector when appropriate
class MainFragment : Fragment(), KodeinAware {

    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein by activityKodein

    // will be the same instance as the coffeeMaker in MainActivity
    val coffeeMaker: Kettle<Coffee> by instance()

    val log: Logger by instance()

    val textView: TextView by lazy { activity.findViewById<TextView>(R.id.text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log.log("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log.callback = {
            textView.text = log.text
        }

        log.log("Starting to brew coffee using $coffeeMaker")

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 3000)

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 6000)
    }
}
