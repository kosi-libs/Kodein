package com.github.salomonbrys.kodein

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Class used to get a generic type at runtime
 */
public abstract class TypeToken<T> {
    public val type: Type

    protected constructor() {
        this.type = extractType()
    }

    private fun extractType(): Type {
        val t = javaClass.getGenericSuperclass()

        if (t !is ParameterizedType)
            throw RuntimeException("Invalid TypeToken; must specify type parameters")

        if (t.getRawType() != javaClass<TypeToken<*>>())
            throw RuntimeException("Invalid TypeToken; must directly extend TypeToken")

        return t.getActualTypeArguments()[0]
    }
}

/**
 * Function used to get a generic type at runtime
 */
public inline fun <reified T> typeToken(): Type = (object : TypeToken<T>() {}).type
