package org.kodein.di.android.support

import org.kodein.di.android.closestKodein
import android.support.v4.app.Fragment
import android.support.v4.content.Loader


/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Fragment.closestKodein() = closestKodein { activity!! }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Loader<*>.closestKodein() = closestKodein { context }
