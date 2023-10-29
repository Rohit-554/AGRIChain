package io.jadu.agrichain.farmer.models.repository

import io.jadu.agrichain.farmer.data.FarmerApiService
import io.jadu.agrichain.farmer.models.dtos.AddProduct
import io.jadu.agrichain.farmer.models.dtos.AddShop
import io.jadu.agrichain.farmer.models.dtos.FarmerAuth
import okhttp3.MultipartBody
import javax.inject.Inject

class FarmerRepository @Inject constructor(private val farmerApiService: FarmerApiService) {

    suspend fun farmerAuth(role: String, phoneNo: String) =
        farmerApiService.getFarmerAuth(FarmerAuth(role, phoneNo))



    suspend fun addProduct(
        farmerId: String,
        name: String,
        description: String,
        price: Int,
        quantity: Int,
        availability: String,
        image_url: String,
        sow_date: String,
        harvest_date: String,
    ) =
        farmerApiService.addProduct(
            AddProduct(
                farmerId,
                name,
                description,
                price,
                quantity,
                availability,
                image_url,
                sow_date,
                harvest_date,
            )
        )



    suspend fun getFarmerProductList(addProduct: AddProduct) = farmerApiService.getFarmerProductList(addProduct)

    suspend fun getProduct(productId: String) = farmerApiService.getProduct(productId)

    suspend fun getProducts(userId: String) = farmerApiService.getProducts(userId)

}