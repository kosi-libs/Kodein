package org.kodein.di.android.support

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import org.kodein.di.*
import org.kodein.di.android.di


/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Fragment.di() = di { requireActivity() }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun Fragment.kodein() = di()

/**
 * Alias to `di`
 */
fun Fragment.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun Fragment.closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.di() = di { context }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun Loader<*>.kodein() = di()

/**
 * Alias to `di`
 */
fun Loader<*>.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun Loader<*>.closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AndroidViewModel.di() = di(getApplication<Application>())
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun AndroidViewModel.kodein() = di()

/**
 * Alias to `di`
 */
fun AndroidViewModel.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun AndroidViewModel.closestKodein() = di()