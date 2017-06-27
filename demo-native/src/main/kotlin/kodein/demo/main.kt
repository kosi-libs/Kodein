package kodein.demo

class A

fun main(args: Array<String>) {

    // While this crashes, Kodein cannot be ported to Native!
    println(A::class)
}
