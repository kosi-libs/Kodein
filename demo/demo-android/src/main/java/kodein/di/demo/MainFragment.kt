package kodein.di.demo

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kodein.di.demo.coffee.Coffee
import kodein.di.demo.coffee.Kettle
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainFragment : Fragment(), KodeinAware {

    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein by closestKodein()

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
