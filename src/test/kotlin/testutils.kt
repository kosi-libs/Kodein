
public  inline fun <reified T : Throwable> assertThrown(body: () -> Unit) {
    try {
        body()
    }
    catch (t: Throwable) {
        if (t is T)
            return
        throw t
    }
    throw AssertionError("Exepected ${javaClass<T>().getName()} to be thrown")
}
