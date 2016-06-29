package com.github.salomonbrys.kodein

import java.lang.reflect.Type

operator fun Map<Kodein.Key, Factory<*, *>>.contains(bind: Kodein.Bind): Boolean = any { it.key.bind == bind }

operator fun Map<Kodein.Key, Factory<*, *>>.contains(type: Type): Boolean = any { it.key.bind.type == type }
operator fun Map<Kodein.Key, Factory<*, *>>.contains(type: TypeToken<*>): Boolean = contains(type.type)

fun Map<Kodein.Key, Factory<*, *>>.factoryArgumentTypes(bind: Kodein.Bind): List<Type> = filter { it.key.bind == bind } .map { it.key.argType }

fun Map<Kodein.Key, Factory<*, *>>.tags(type: Type): List<Any?> = filter { it.key.bind.type == type } .map { it.key.bind.tag }
fun Map<Kodein.Key, Factory<*, *>>.tags(type: TypeToken<*>): List<Any?> = filter { it.key.bind.type == type.type } .map { it.key.bind.tag }

fun Map<Kodein.Key, Factory<*, *>>.types(tag: Any?): List<Type> = filter { it.key.bind.tag == tag } .map { it.key.bind.type }

val Map<Kodein.Key, Factory<*, *>>.description: String get() = map { "        ${it.key.bind.toString()} with ${it.value.description}" }.joinToString("\n")
