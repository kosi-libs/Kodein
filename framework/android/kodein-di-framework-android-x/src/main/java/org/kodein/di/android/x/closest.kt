package org.kodein.di.android.x

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.loader.content.Loader
import org.kodein.di.android.DIPropertyDelegateProvider
import org.kodein.di.android.LazyContextDIPropertyDelegateProvider
import org.kodein.di.android.closestDI


/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun Fragment.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { requireActivity() }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun Loader<*>.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun AndroidViewModel.closestDI(): LazyContextDIPropertyDelegateProvider = closestDI(getApplication<Application>())