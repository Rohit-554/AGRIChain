package io.jadu.agrichain.farmer.models.dtos

import okhttp3.MultipartBody

data class AddProduct (
    val farmer_id:String,
    val name:String,
    val description:String,
    val price:Int,
    val quantity:Int,
    val availability:String,
    val image_url:String = "https://upload.wikimedia.org/wikipedia/commons/8/8a/Banana-Single.jpg" ,
    val sow_date:String,
    val harvest_date:String
)