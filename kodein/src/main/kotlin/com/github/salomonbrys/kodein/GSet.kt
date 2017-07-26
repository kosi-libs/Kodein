@file:Suppress("unused")

package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.bindings.ArgSetBinding
import com.github.salomonbrys.kodein.bindings.InSet
import com.github.salomonbrys.kodein.bindings.SetBinding

@Suppress("RemoveExplicitTypeArguments")
inline fun <reified T: Any> Kodein.Builder.setBinding() = SetBinding(generic<T>(), generic<Set<T>>())

@Suppress("RemoveExplicitTypeArguments")
inline fun <reified A, reified T: Any> Kodein.Builder.argSetBinding() = ArgSetBinding(generic<A>(), generic<T>(), generic<Set<T>>())

inline fun <reified T: Any> Kodein.Builder.TypeBinder<T>.inSet() = InSet(generic())
