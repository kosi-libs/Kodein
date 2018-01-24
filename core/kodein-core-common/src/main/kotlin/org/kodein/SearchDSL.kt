package org.kodein

open class SearchSpecs(
        var contextType: TypeToken<*>? = null,
        var argType: TypeToken<*>? = null,
        var type: TypeToken<*>? = null,
        var tag: Any? = NoDefinedTag
) {
    internal object NoDefinedTag

    override fun toString(): String {
        val args = ArrayList<String>()
        if (contextType != null)
            args += "contextType=${contextType?.simpleDispString()}"
        if (argType != null)
            args += "argType=${argType?.simpleDispString()}"
        if (type != null)
            args += "type=${type?.simpleDispString()}"
        if (tag != NoDefinedTag)
            args += "tag=$tag"
        return "[${args.joinToString(", ")}]"
    }


}

interface SearchDSL {

    interface Spec {
        fun apply(specs: SearchSpecs)
        companion object {
            internal operator fun invoke(f: SearchSpecs.() -> Unit) = object : Spec { override fun apply(specs: SearchSpecs) = f(specs) }
        }
    }

    class Binding(val type: TypeToken<*>, val tag: Any? = null) : Spec {
        override fun apply(specs: SearchSpecs) { specs.type = type ; if (tag != null) specs.tag = tag }
    }

    infix fun SearchSpecs.with(spec: Spec): SearchSpecs = apply { spec.apply(this) }
    infix fun SearchSpecs.and(spec: Spec): SearchSpecs = apply { spec.apply(this) }

    fun Context(contextType: TypeToken<*>) = Spec { this.contextType = contextType }

    fun Argument(argumentType: TypeToken<*>) = Spec { this.argType = argumentType }

    fun tag(tag: Any?) = Spec { this.tag = tag }
}
