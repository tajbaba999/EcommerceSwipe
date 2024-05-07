package com.example.e_commerce_swipe

import retrofit2.Call
import retrofit2.http.GET

interface ProductApiInterface {

    @GET("get")
    fun getData() : Call<List<ProductItem>>

}