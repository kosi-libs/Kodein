@file:Suppress("unused")

package com.github.salomonbrys.kodein.erased

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bindings.ArgSetBinding
import com.github.salomonbrys.kodein.bindings.InSet
import com.github.salomonbrys.kodein.bindings.SetBinding
import com.github.salomonbrys.kodein.erased

@Suppress("RemoveExplicitTypeArguments")
inline fun <reified T: Any> Kodein.Builder.setBinding() = SetBinding(erased<T>(), erasedComp1<Set<T>, T>())

@Suppress("RemoveExplicitTypeArguments")
inline fun <reified A, reified T: Any> Kodein.Builder.argSetBinding() = ArgSetBinding(erased<A>(), erased<T>(), erasedComp1<Set<T>, T>())

inline fun <reified T: Any> Kodein.Builder.TypeBinder<T>.inSet() = InSet(erasedComp1<Set<T>, T>())
