package org.kodein.di.android.x

import androidx.fragment.app.Fragment
import androidx.loader.content.Loader
import org.kodein.di.android.closestKodein


/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Fragment.closestKodein() = closestKodein { activity!! }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Loader<*>.closestKodein() = closestKodein { context }
