/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util


object Arrays {

    fun equals(a: Array<*>, a2: Array<*>): Boolean {
        if (a === a2)
            return true

        val length = a.size
        if (a2.size != length)
            return false

        for (i in 0..length - 1) {
            val o1 = a[i]
            val o2 = a2[i]
            if (!(if (o1 == null) o2 == null else o1 == o2))
                return false
        }

        return true
    }

    fun hashCode(a: Array<*>): Int {
        var result = 1

        for (element in a)
            result = 31 * result + (element?.hashCode() ?: 0)

        return result
    }

}
