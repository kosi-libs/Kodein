package org.kodein.di.android.x

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.loader.content.Loader
import org.kodein.di.*
import org.kodein.di.android.closestDI
import org.kodein.di.android.di


@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun Fragment.di() = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Fragment.closestDI() = closestDI { requireActivity() }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun Loader<*>.di() = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.closestDI() = closestDI { context }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun AndroidViewModel.di() = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AndroidViewModel.closestDI() = closestDI(getApplication<Application>())