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
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val fab = findViewById<FloatingActionButton>(R.id.floating_action_button)

        fab.setOnClickListener {
            val intent = Intent(this, Productadd::class.java)
            startActivity(intent)
        }

    }

    private fun getProducts() {
        val apiService = RetrofitClient.getClient(BASE_URL).create(ProductApiInterface::class.java)

        apiService.getData().enqueue(object : Callback<List<ProductItem>> {
            override fun onResponse(
                call: Call<List<ProductItem>>,
                response: Response<List<ProductItem>>
            ) {
                if(response.isSuccessful) {
                    val data = response.body()!!
                    prductAdapter = ProductAdapter(baseContext, data)
                    rvView.adapter = prductAdapter
                }
            }

            override fun onFailure(call: Call<List<ProductItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
