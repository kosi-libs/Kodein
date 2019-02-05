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
// Deprecated since 6.1
@Deprecated("closestKodein has been renamed kodein", ReplaceWith("kodein()", "org.kodein.di.android.x.kodein"))
fun Fragment.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Loader<*>.kodein() = kodein { context }
// Deprecated since 6.1
@Deprecated("closestKodein has been renamed kodein", ReplaceWith("kodein()", "org.kodein.di.android.x.kodein"))
fun Loader<*>.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun AndroidViewModel.kodein() = org.kodein.di.android.kodein(getApplication<Application>())
