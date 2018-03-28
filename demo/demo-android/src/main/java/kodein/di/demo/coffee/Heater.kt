package kodein.di.demo.coffee

interface Heater {
    fun on()
    fun off()
    val isHot: Boolean
}
