package com.github.salomonbrys.kodein.test

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.TT
import com.github.salomonbrys.kodein.bindings.SingletonBinding
import com.github.salomonbrys.kodein.erased.instance
import kotlin.test.Test

class ErasedJsTests {

    @Test
    fun test28_0_ManualTyping() {

        open class Resource
        class SubResource : Resource()

        val resourceClass: JsClass<out Resource> = SubResource::class.js

        val kodein = Kodein {
            Bind(TT(resourceClass)) with SingletonBinding(TT(resourceClass)) { SubResource() }
        }

        kodein.instance<SubResource>()
    }

}
