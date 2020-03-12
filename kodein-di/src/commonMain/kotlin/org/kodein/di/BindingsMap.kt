package org.kodein.di

import org.kodein.di.bindings.*

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIDefining<C,A,T>"), DeprecationLevel.ERROR)
typealias KodeinDefining<C,A,T> = DIDefining<C,A,T>

/**
 * A binding that is being defined inside a [DI.Builder] bloc.
 *
 * The associated [DITree] has not be created yet.
 *
 * @property binding The binding
 * @property fromModule The module name that defined the binding (for debug)
 */
open class DIDefining<C : Any, A, T: Any>(val binding: DIBinding<C, A, T>, val fromModule: String?)

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIDefinition<C,A,T>"), DeprecationLevel.ERROR)
typealias KodeinDefinition<C,A,T> = DIDefinition<C,A,T>
/**
 * A definition is a binding that is associated to a [DITree].
 *
 * @property binding The binding
 * @property fromModule The module name that defined the binding (for debug)
 * @property tree The tree that this binding relates to.
 */
class DIDefinition<C : Any, A, T: Any>(binding: DIBinding<C, A, T>, fromModule: String?, val tree: DITree) : DIDefining<C, A, T>(binding, fromModule)

/**
 * A Map containing all bindings associated to their keys
 */
typealias BindingsMap = Map<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>>

private fun BindingsMap.descriptionImpl(withOverrides: Boolean, ident: Int, keyBindDisp: DI.Key<*, *, *>.() -> String, bindingDisp: DIBinding<*, *, *>.() -> String): String {

    fun StringBuilder.appendBindings(ident: Int, entries: List<Map.Entry<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>>>) =
            entries.forEach {
                val keyDescription = it.key.keyBindDisp()
                append("${" ".repeat(ident)}$keyDescription with ${it.value.first().binding.bindingDisp()}")
                if (withOverrides) {
                    val subIdent = keyDescription.length - 4
                    it.value.subList(1, it.value.size).forEach {
                        append("${" ".repeat(subIdent)}overrides ${it.binding.bindingDisp()}")
                    }
                }
                append("\n")
            }

    val byModule = entries.groupBy { it.value.first().fromModule }
    val modules = byModule.keys.filterNotNull().sorted()

    return buildString {
        byModule[null]?.let { appendBindings(ident, it) }

        modules.forEach {
            append("${" ".repeat(ident)}module $it {\n")
            appendBindings(ident + 4, byModule[it]!!)
            append("${" ".repeat(ident)}}\n")
        }
    }
}


/**
 * The description of all bindings in this map, using type simple display names.
 *
 * @receiver The bindings map.
 */
fun BindingsMap.description(withOverrides: Boolean = false, ident: Int = 8): String = descriptionImpl(withOverrides, ident, DI.Key<*, *, *>::bindDescription, DIBinding<*, *, *>::description)

/**
 * The description of all bindings in this map, using type full display names.
 *
 * @receiver The bindings map.
 */
fun BindingsMap.fullDescription(withOverrides: Boolean = false, ident: Int = 8): String = descriptionImpl(withOverrides, ident, DI.Key<*, *, *>::bindFullDescription, DIBinding<*, *, *>::fullDescription)
