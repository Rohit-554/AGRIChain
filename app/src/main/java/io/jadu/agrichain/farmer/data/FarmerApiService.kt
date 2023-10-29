package io.jadu.agrichain.farmer.data

import io.jadu.agrichain.farmer.models.dtos.AddProduct
import io.jadu.agrichain.farmer.models.dtos.AddShop
import io.jadu.agrichain.farmer.models.dtos.FarmerAuth
import io.jadu.agrichain.farmer.models.dtos.Shop
import io.jadu.agrichain.farmer.models.dtos.productDetails
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FarmerApiService {
    companion object {
        const val BASE_URL = "https://90f2-49-249-72-18.ngrok-free.app"
    }

    @POST("/mongo/user")
    suspend fun getFarmerAuth(@Body farmerAuth: FarmerAuth)

    @POST("/mongo/product")
    suspend fun addProduct(@Body addProduct: AddProduct)

    @POST("/api/shop/{shopId}/addProduct")
//    suspend fun addProduct(
//        @Part("name") name: String,
//        @Part("description") description: String,
//        @Part("price") price: String,
//        @Part("quantity") quantity: String,
//        @Part("availability") availability: String,
//        @Part image_url: MultipartBody.Part,
//        @Part("sow_date") sow_date: String,
//        @Part("harvest_date") harvest_date: String,
//        @Path("shopId") shopId: String
//    )

    @GET("mongo/user/653dbecc0a70753711401a8b")
    suspend fun getFarmerId(@Path("phoneNo") phoneNo: String): Response<Shop>

    @POST("mongo/product")
    suspend fun getFarmerProductList(@Body addProduct: AddProduct)

    @GET("mongo/product/{productId}")
    suspend fun getProduct(@Path("productId") productId: String): Response<productDetails>

    @GET("mongo/products/{userId}")
    suspend fun getProducts(@Path("userId") userId: String): Response<List<productDetails>>
}