package org.kodein.di.android.support

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import org.kodein.di.*
import org.kodein.di.android.kodein


/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun Fragment.kodein() = kodein { requireActivity() }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Fragment.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun Loader<*>.kodein() = kodein { context }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Loader<*>.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun AndroidViewModel.kodein() = kodein(getApplication<Application>())

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun AndroidViewModel.closestKodein() = kodein()