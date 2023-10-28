package io.jadu.agrichain.farmer.data

import io.jadu.agrichain.farmer.models.dtos.AddShop
import io.jadu.agrichain.farmer.models.dtos.FarmerAuth
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FarmerApiService {

    companion object {
        const val BASE_URL = "https://angry-teal-bluefish.cyclic.app/"
    }

    @POST("/api/user/auth")
    suspend fun getFarmerAuth(@Body farmerAuth: FarmerAuth)

    @POST("/api/shop/addShop")
    suspend fun addShop(@Body addShop: AddShop)



}