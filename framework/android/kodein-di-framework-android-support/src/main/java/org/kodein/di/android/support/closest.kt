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

/**
 * Alias to `di`
 */
fun Fragment.closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.di() = di { context }

/**
 * Alias to `di`
 */
fun Loader<*>.closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AndroidViewModel.di() = di(getApplication<Application>())

/**
 * Alias to `di`
 */
fun AndroidViewModel.closestDI() = di()