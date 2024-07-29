package com.osh.weather_sdk.common.screen.common.model

import com.osh.weather_sdk.common.model.ApiResult

internal fun <T : Any> ApiResult<T>.toUIState(): LoadableState<T> {
    return when (this) {
        is ApiResult.Error -> LoadableState.Error(this.message)
        is ApiResult.Success -> LoadableState.Ready(this.data)
    }
}