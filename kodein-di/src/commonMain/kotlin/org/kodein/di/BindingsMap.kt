package org.kodein.di

import org.kodein.di.bindings.*

/**
 * A binding that is being defined inside a [DI.Builder] bloc.
 *
 * The associated [DITree] has not be created yet.
 *
 * @property binding The binding
 * @property fromModule The module name that defined the binding (for debug)
 */
public open class DIDefining<C : Any, A, T: Any>(public val binding: DIBinding<C, A, T>, public val fromModule: String?)

/**
 * A definition is a binding that is associated to a [DITree].
 *
 * @property binding The binding
 * @property fromModule The module name that defined the binding (for debug)
 * @property tree The tree that this binding relates to.
 */
public class DIDefinition<C : Any, A, T: Any>(binding: DIBinding<C, A, T>, fromModule: String?, public val tree: DITree) : DIDefining<C, A, T>(binding, fromModule)

/**
 * A Map containing all bindings associated to their keys
 */
public typealias BindingsMap = Map<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>>

private fun BindingsMap.descriptionImpl(withOverrides: Boolean, ident: Int, keyBindDisp: DI.Key<*, *, *>.() -> String, bindingDisp: DIBinding<*, *, *>.() -> String): String {

    fun StringBuilder.appendBindings(ident: Int, entries: List<Map.Entry<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>>>) =
            entries.forEach {
                val keyDescription = it.key.keyBindDisp()
                append("${" ".repeat(ident)}$keyDescription { ${it.value.first().binding.bindingDisp()} }")
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
public fun BindingsMap.description(withOverrides: Boolean = false, ident: Int = 8): String = descriptionImpl(withOverrides, ident, DI.Key<*, *, *>::bindDescription, DIBinding<*, *, *>::description)

/**
 * The description of all bindings in this map, using type full display names.
 *
 * @receiver The bindings map.
 */
public fun BindingsMap.fullDescription(withOverrides: Boolean = false, ident: Int = 8): String = descriptionImpl(withOverrides, ident, DI.Key<*, *, *>::bindFullDescription, DIBinding<*, *, *>::fullDescription)
