package org.kodein.di

import org.kodein.di.bindings.KodeinBinding

/**
 * A binding that is being defined inside a [Kodein.Builder] bloc.
 *
 * The associated [KodeinTree] has not be created yet.
 *
 * @property binding The binding
 * @property fromModule The module name that defined the binding (for debug)
 */
open class KodeinDefining<C, A, T: Any>(val binding: KodeinBinding<C, A, T>, val fromModule: String?)

/**
 * A definition is a binding that is associated to a [KodeinTree].
 *
 * @property binding The binding
 * @property fromModule The module name that defined the binding (for debug)
 * @property tree The tree that this binding relates to.
 */
class KodeinDefinition<C, A, T: Any>(binding: KodeinBinding<C, A, T>, fromModule: String?, val tree: KodeinTree) : KodeinDefining<C, A, T>(binding, fromModule)

/**
 * A Map containing all bindings associated to their keys
 */
typealias BindingsMap = Map<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>>

private fun BindingsMap.descriptionImpl(withOverrides: Boolean, ident: Int, keyBindDisp: Kodein.Key<*, *, *>.() -> String, bindingDisp: KodeinBinding<*, *, *>.() -> String): String {

    fun StringBuilder.appendBindings(ident: Int, entries: List<Map.Entry<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>>>) =
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
fun BindingsMap.description(withOverrides: Boolean = false, ident: Int = 8): String = descriptionImpl(withOverrides, ident, Kodein.Key<*, *, *>::bindDescription, KodeinBinding<*, *, *>::description)

/**
 * The description of all bindings in this map, using type full display names.
 *
 * @receiver The bindings map.
 */
fun BindingsMap.fullDescription(withOverrides: Boolean = false, ident: Int = 8): String = descriptionImpl(withOverrides, ident, Kodein.Key<*, *, *>::bindFullDescription, KodeinBinding<*, *, *>::fullDescription)
