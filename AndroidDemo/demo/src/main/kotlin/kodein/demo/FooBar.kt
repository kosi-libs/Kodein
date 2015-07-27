package kodein.demo

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance

public interface Foo
public interface Bar<T> {
    public val name: String
}
public class FooBarImpl : Bar<Foo> {
    override val name = "FooBarImpl"
}

public fun Kodein.Builder.bindFooBar() {
    bind<Bar<Foo>>() with instance(FooBarImpl())
}
