package io.jadu.agrichain.farmer.models.repository

import io.jadu.agrichain.farmer.data.FarmerApiService
import io.jadu.agrichain.farmer.models.dtos.FarmerAuth
import javax.inject.Inject

class FarmerRepository @Inject constructor(private val farmerApiService: FarmerApiService) {

    suspend fun farmerAuth(role:String, phoneNo: String) = farmerApiService.getFarmerAuth(FarmerAuth(role,phoneNo))
}