package kodein.demo

public class Logger {

    public var text: String = ""
        private set

    public var callback: (() -> Unit)? = null

    public fun log(msg: String) {
        text += msg + "\n"
        callback?.invoke()
    }
}
