package com.example.e_commerce_swipe

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ProductApiInterface {

    @GET("get")
    fun getData() : Call<List<ProductItem>>
}