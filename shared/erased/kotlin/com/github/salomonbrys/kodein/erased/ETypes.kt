package com.github.salomonbrys.kodein.erased

import com.github.salomonbrys.kodein.CompositeTypeToken
import com.github.salomonbrys.kodein.erased

inline fun <reified T, reified A1> erasedComp1() = CompositeTypeToken(erased<T>(), erased<A1>())
inline fun <reified T, reified A1, reified A2> erasedComp2() = CompositeTypeToken(erased<T>(), erased<A1>(), erased<A2>())
inline fun <reified T, reified A1, reified A2, reified A3> erasedComp3() = CompositeTypeToken(erased<T>(), erased<A1>(), erased<A2>(), erased<A3>())
