package com.example.e_commerce_swipe

data class ProductResponse(
    val message: String,
    val product_details: ProductDetails,
    val product_id: Int,
    val success: Boolean
)
data class ProductDetails(
    val image: String,
    val price: Double?,
    val product_name: String,
    val product_type: String,
    val tax: Double?
)
