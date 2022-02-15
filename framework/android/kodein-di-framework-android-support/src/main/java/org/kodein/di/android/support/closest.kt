package org.kodein.di.android.support

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import org.kodein.di.*
import org.kodein.di.android.closestDI
import org.kodein.di.android.di


@Deprecated(
    message = "di() function leads to import conflicts. please replace with closestDI().",
    replaceWith = ReplaceWith("closestDI()", "org.kodein.di.android"),
    level = DeprecationLevel.ERROR
)
fun Fragment.di() = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Fragment.closestDI() = closestDI { requireActivity() }

@Deprecated(
    message = "di() function leads to import conflicts. please replace with closestDI().",
    replaceWith = ReplaceWith("closestDI()", "org.kodein.di.android"),
    level = DeprecationLevel.ERROR
)
fun Loader<*>.di() = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.closestDI() = closestDI { context }

@Deprecated(
    message = "di() function leads to import conflicts. please replace with closestDI().",
    replaceWith = ReplaceWith("closestDI()", "org.kodein.di.android"),
    level = DeprecationLevel.ERROR
)
fun AndroidViewModel.di() = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AndroidViewModel.closestDI() = closestDI(getApplication<Application>())