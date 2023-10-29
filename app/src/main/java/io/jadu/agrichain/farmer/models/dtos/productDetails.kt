package io.jadu.agrichain.farmer.models.dtos

data class productDetails(
    val availability: String,
    val description: String,
    val farmer_id: String,
    val harvest_date: String,
    val id: String,
    val image_url: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val sow_date: String
)