package com.example.e_commerce_swipe

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ProductpostService {
    @FormUrlEncoded
    @POST("add")
    fun addProduct(
        @Field("product_name") productName: String,
        @Field("product_type") productType: String,
        @Field("price") price: String,
        @Field("tax") tax: String,
        @Part image: MultipartBody.Part
    ): Call<ProductResponse>
}