package com.example.e_commerce_swipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_swipe.activity.LoginActivity
import com.example.e_commerce_swipe.activity.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var BASE_URL = "https://app.getswipe.in/api/public/";
    lateinit var rvView : RecyclerView
    lateinit var prductAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvView = findViewById(R.id.recyclerView)

        rvView.layoutManager = LinearLayoutManager(this)

        getProducts()

    }

    private fun getProducts() {
            var retrofit    = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductApiInterface::class.java)

        var retorData = retrofit.getData()

        retorData.enqueue(object : Callback<List<ProductItem>>{
            override fun onResponse(
                call: Call<List<ProductItem>>,
                response: Response<List<ProductItem>>
            ) {
                if(response.isSuccessful) {
                    var data = response.body()!!
//                    Toast.makeText(this@MainActivity, " datas ${data.toString()}", Toast.LENGTH_LONG)
//                        .show()
//                    Log.d("datas", data.toString())

                    prductAdapter = ProductAdapter(baseContext, data)
                    rvView.adapter = prductAdapter
                }
            }

            override fun onFailure(call: Call<List<ProductItem>>, t: Throwable) {

            }

        })
    }
}