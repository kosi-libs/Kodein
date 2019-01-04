package kodein.di.demo

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kodein.di.demo.coffee.Coffee
import kodein.di.demo.coffee.Kettle
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    // will be the same instance as the coffeeMaker in MainActivity
    val coffeeMaker: Kettle<Coffee> by instance()

    val log: Logger by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log.log("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()

        log.callback = {
            text.text = log.text
        }

        log.log("Starting to brew coffee using $coffeeMaker")

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 3000)

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 6000)
    }

    override fun onStop() {
        log.callback = null
        super.onStop()
    }
}
