package org.kodein.di.android.x

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.loader.content.Loader
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