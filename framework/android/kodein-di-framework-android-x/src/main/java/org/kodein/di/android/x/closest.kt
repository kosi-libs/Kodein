package org.kodein.di.android.x

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.loader.content.Loader
import org.kodein.di.android.kodein


/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Fragment.kodein() = kodein { requireActivity() }

/**
 * Alias to `kodein`
 */
fun Fragment.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Loader<*>.kodein() = kodein { context }

/**
 * Alias to `kodein`
 */
fun Loader<*>.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun AndroidViewModel.kodein() = kodein(getApplication<Application>())

/**
 * Alias to `kodein`
 */
fun AndroidViewModel.closestKodein() = kodein()
