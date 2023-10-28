package io.jadu.agrichain.farmer.models.repository

import io.jadu.agrichain.farmer.data.FarmerApiService
import io.jadu.agrichain.farmer.models.dtos.AddShop
import io.jadu.agrichain.farmer.models.dtos.FarmerAuth
import io.jadu.agrichain.farmer.models.dtos.addProduct
import okhttp3.MultipartBody
import javax.inject.Inject

class FarmerRepository @Inject constructor(private val farmerApiService: FarmerApiService) {

    suspend fun farmerAuth(role: String, phoneNo: String) =
        farmerApiService.getFarmerAuth(FarmerAuth(role, phoneNo))

    suspend fun addShop(shop_name: String, description: String, location: String, phoneNo: String) =
        farmerApiService.addShop(AddShop(shop_name, description, location, phoneNo))

    suspend fun addProduct(
        name: String,
        description: String,
        price: String,
        quantity: String,
        availability: String,
        image_url: MultipartBody.Part,
        sow_date: String,
        harvest_date: String,
        shopId: String
    ) =
        farmerApiService.addProduct(
                name,
                description,
                price,
                quantity,
                availability,
                image_url,
                sow_date,
                harvest_date,
                shopId
        )
}