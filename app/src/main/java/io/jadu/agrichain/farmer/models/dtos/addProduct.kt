package io.jadu.agrichain.farmer.models.dtos

import okhttp3.MultipartBody

data class addProduct (
    val name:String,
    val description:String,
    val price:String,
    val quantity:String,
    val availability:String,
    val image_url:MultipartBody.Part,
    val sow_date:String,
    val harvest_date:String
)