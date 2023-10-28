package io.jadu.agrichain.farmer.util

import io.jadu.agrichain.farmer.models.dtos.FarmerAuth


sealed class GetApiState{
    data object Loading: GetApiState()
    class Success(val data: List<FarmerAuth>) : GetApiState()
    class Error(val msg: Throwable) : GetApiState()
    data object Empty: GetApiState()
}
