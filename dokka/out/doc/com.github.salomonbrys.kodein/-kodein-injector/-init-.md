[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`KodeinInjector()`

An injector is an object which creates injected property delegates **before** having access to a Kodein instance.

For example, in Android, you cant access the Kodein instance before onCreate is called:

```
class MyActivity : Activity() {
    val injector: KodeinInjector()
    val engine: Engine by injector.instance()
    val random: () -&gt; Int by injector.provider("random")
    fun onCreate(savedInstanceState: Bundle) {
        injector.inject(appKodein()) // See Android's documentation for appKodein
        // Here, you can now access engine and random properties.
    }
}
```

