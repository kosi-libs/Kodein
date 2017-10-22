package org.kodein

import org.kodein.bindings.KodeinBinding

/**
 * A Map containing all bindings associated to their keys
 */
typealias BindingsMap = Map<Kodein.Key<*, *>, KodeinBinding<*, *>>

/**
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 * @param bind The bind to look for.
 * @return Whether or not this binding can be found in the binding map, whatever the factory argument type.
 */
operator fun BindingsMap.contains(bind: Kodein.Bind<*>): Boolean = any { it.key.bind == bind }

/**
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 * @param type The type to look for.
 * @return Whether or not this type is bound in the binding map, whatever the tag or the factory argument type.
 */
operator fun BindingsMap.contains(type: TypeToken<*>): Boolean = any { it.key.bind.type == type }

/**
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 * @param bind The bind to look for.
 * @return The list of argument type that are bound with this binding. Each entry represents a different [Kodein.Key].
 */
fun BindingsMap.factoryArgumentTypes(bind: Kodein.Bind<*>): List<TypeToken<*>> = filter { it.key.bind == bind } .map { it.key.argType }

/**
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 * @param type The type to look for.
 * @return The list of tags that are bound with this type. Each entry represents a different [Kodein.Bind],
 *         but there may be multiple [Kodein.Key] for the same [Kodein.Bind].
 */
fun BindingsMap.tags(type: TypeToken<*>): List<Any?> = filter { it.key.bind.type == type } .map { it.key.bind.tag } .distinct()

/**
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 * @param tag The tag to look for.
 * @return The list of types that are bound with this tag. Each entry represents a different [Kodein.Bind],
 *         but there may be multiple [Kodein.Key] for the same [Kodein.Bind].
 */
fun BindingsMap.types(tag: Any?): List<TypeToken<*>> = filter { it.key.bind.tag == tag } .map { it.key.bind.type } .distinct()

/**
 * The description of all bindings in this map, using type simple display names.
 *
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 */
val BindingsMap.description: String get() = map { "        ${it.key.bind.description} with ${it.value.description}" }.joinToString("\n")

/**
 * The description of all bindings in this map, using type full display names.
 *
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 */
val BindingsMap.fullDescription: String get() = map { "        ${it.key.bind.fullDescription} with ${it.value.fullDescription}" }.joinToString("\n")
