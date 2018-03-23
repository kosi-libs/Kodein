package org.kodein.di

/**
 * Defines the specs to search bindings from (in)complete data with [CopySpecs] or [KodeinTree.find].
 *
 * @property contextType The type of context the bindings must take.
 * @property argType The type of argument the bindings must take.
 * @property type The type the bindings must return.
 * @property tag The tag the bindings must be associated with.
 */
open class SearchSpecs(
        var contextType: TypeToken<*>? = null,
        var argType: TypeToken<*>? = null,
        var type: TypeToken<*>? = null,
        var tag: Any? = NoDefinedTag
) {
    internal object NoDefinedTag

    /** @suppress */
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

/**
 * DSL that facilitates the creation of a [SearchSpecs] object.
 */
@Suppress("FunctionName")
open class SearchDSL {

    /**
     * A function that modifies a [SearchSpecs] object to add its constraint.
     */
    interface Spec {
        /**
         * Apply the function.
         *
         * @param specs The object to modify.
         */
        fun apply(specs: SearchSpecs)

        companion object {
            internal operator fun invoke(f: SearchSpecs.() -> Unit) = object : Spec { override fun apply(specs: SearchSpecs) = f(specs) }
        }
    }

    /**
     * A binding return type constrained spec.
     *
     * @property type The type constraint.
     * @property tag An optional tag constraint.
     */
    class Binding(val type: TypeToken<*>, val tag: Any? = null) : Spec {
        override fun apply(specs: SearchSpecs) { specs.type = type ; if (tag != null) specs.tag = tag }
    }

    /**
     * Allows to add a constraints spec.
     *
     * Alias of [and].
     *
     * @param spec The spec constraint to apply to this specs.
     */
    infix fun SearchSpecs.with(spec: Spec): SearchSpecs = apply { spec.apply(this) }

    /**
     * Allows to merge to constraints.
     *
     * Alias of [with].
     *
     * @param spec The spec constraint to apply to this specs.
     */
    infix fun SearchSpecs.and(spec: Spec): SearchSpecs = apply { spec.apply(this) }

    /**
     * Creates a context constrained spec.
     *
     * @param contextType The context type constraint.
     */
    fun Context(contextType: TypeToken<*>) = Spec { this.contextType = contextType }

    /**
     * Creates an argument constrained spec.
     *
     * @param argumentType The argument type constraint.
     */
    fun Argument(argumentType: TypeToken<*>) = Spec { this.argType = argumentType }

    /**
     * Creates a tag constrained spec.
     *
     * @param tag The tag constraint.
     */
    fun tag(tag: Any?) = Spec { this.tag = tag }
}

/**
 * DSL to find bindings.
 *
 * Can be used as such (with [findAllBindings]):
 *
 * ```
 * val bindings = kodein.container.tree.findAllBindings {
 *     +binding<Whatever>()
 *     +tag("my-tag")
 * }
 * ```
 */
class FindDSL : SearchDSL() {
    internal val specs = SearchSpecs()

    /**
     * Register a spec.
     *
     * @receiver The spec to register in this search.
     */
    operator fun Spec.unaryPlus() {
        apply(specs)
    }
}

/**
 * Used to find bindings that match a particular [SearchSpecs].
 */
fun KodeinTree.findAllBindings(f: FindDSL.() -> Unit): List<Pair<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>>> {
    val dsl = FindDSL()
    dsl.f()
    return find(dsl.specs)
}
