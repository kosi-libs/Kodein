package org.kodein

import org.kodein.bindings.KodeinBinding

/**
 * A Map containing all bindings associated to their keys
 */
typealias BindingsMap = Map<Kodein.Key<*, *, *>, List<KodeinBinding<*, *, *>>>

private fun BindingsMap._description(withOverrides: Boolean, ident: Int, keyBindDisp: Kodein.Key<*, *, *>.() -> String, bindingDisp: KodeinBinding<*, *, *>.() -> String, typeDisp: TypeToken<*>.() -> String) =
        this
                .map {
                    buildString {
                        val keyDescription = it.key.keyBindDisp()
                        append("${" ".repeat(ident)}$keyDescription with ${it.value.first().bindingDisp()}")
                        if (withOverrides) {
                            val subIdent = keyDescription.length - 4
                            it.value.subList(1, it.value.size).forEach {
                                append("${" ".repeat(subIdent)}overrides ${it.bindingDisp()}")
                            }
                        }
                    }

                }
                .joinToString("\n")


/**
 * The description of all bindings in this map, using type simple display names.
 *
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 */
fun BindingsMap.description(withOverrides: Boolean = false, ident: Int = 8): String = _description(withOverrides, ident, Kodein.Key<*, *, *>::bindDescription, KodeinBinding<*, *, *>::description, TypeToken<*>::simpleDispString)

/**
 * The description of all bindings in this map, using type full display names.
 *
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 */
fun BindingsMap.fullDescription(withOverrides: Boolean = false, ident: Int = 8): String = _description(withOverrides, ident, Kodein.Key<*, *, *>::bindFullDescription, KodeinBinding<*, *, *>::fullDescription, TypeToken<*>::fullDispString)
