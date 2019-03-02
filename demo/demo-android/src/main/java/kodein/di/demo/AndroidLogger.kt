package kodein.di.demo

class AndroidLogger : CommonLogger {

    var text: String = ""
        private set

    var callback: (() -> Unit)? = null

    override fun log(msg: String) {
        text += "$msg\n"
        callback?.invoke()
    }
}
