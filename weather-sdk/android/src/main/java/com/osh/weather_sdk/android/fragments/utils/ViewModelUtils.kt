package com.osh.weather_sdk.android.fragments.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

internal inline fun <reified T : ViewModel> findOrCreateViewModel(
    storeOwner: ViewModelStoreOwner,
    crossinline create: () -> T
): T =
    findOrCreateViewModel(storeOwner = storeOwner, key = null, create = create)

/** @SelfDocumented */
internal inline fun <reified T : ViewModel> findOrCreateViewModel(
    storeOwner: ViewModelStoreOwner,
    key: String?,
    crossinline create: () -> T
): T =
    ViewModelProvider(
        storeOwner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return create() as T
            }
        }
    )
        .run {
            if (key != null) {
                get(key, T::class.java)
            } else {
                get(T::class.java)
            }
        }