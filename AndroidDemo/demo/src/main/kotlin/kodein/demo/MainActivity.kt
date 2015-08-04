package kodein.demo

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinHolder
import com.github.salomonbrys.kodein.injectInstance
import kodein.demo.coffee.Coffee
import kodein.demo.coffee.Kettle
import kotlin.properties.Delegates

public class MainActivity : Activity(), KodeinHolder {

    override val kodein: Kodein by Delegates.lazy { (getApplication() as DemoApplication).kodein }

    public val coffeeMaker: Kettle<Coffee> by injectInstance()
    public val log: Logger by injectInstance()

    public val textView: TextView by Delegates.lazy { findViewById(R.id.text) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log.callback = {
            textView.setText(log.text)
        }

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 3000)

        Handler().postDelayed({
            coffeeMaker.brew()
        }, 6000)
    }

}
