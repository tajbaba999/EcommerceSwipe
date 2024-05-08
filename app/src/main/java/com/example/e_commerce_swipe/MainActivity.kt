package com.example.e_commerce_swipe

import android.content.Intent
import android.media.browse.MediaBrowser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
//import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_swipe.activity.LoginActivity
import com.example.e_commerce_swipe.activity.SignupActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    var BASE_URL = "https://app.getswipe.in/api/public/";
    lateinit var rvView : RecyclerView
    lateinit var prductAdapter: ProductAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private var productList = mutableListOf<ProductItem>()
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = Firebase.analytics
        firebaseAuth = FirebaseAuth.getInstance()

        rvView = findViewById(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)


        rvView.layoutManager = LinearLayoutManager(this)

        getProducts()

        val fab = findViewById<FloatingActionButton>(R.id.floating_action_button)

        fab.setOnClickListener {
            val intent = Intent(this, Productadd::class.java)
            startActivity(intent)
        }

        val btn = findViewById<ImageButton>(R.id.logout)
        btn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterProducts(it) }
                return true
            }
        })

        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : MediaBrowser.ConnectionCallback(), ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false
                noInternetConnectionTitle = "No Internet"
                noInternetConnectionMessage =
                    "Check your Internet connection and try again."
                showInternetOnButtons = true
                pleaseTurnOnText = "Please turn on"
                wifiOnButtonText = "Wifi"
                mobileDataOnButtonText = "Mobile data"

                onAirplaneModeTitle = "No Internet"
                onAirplaneModeMessage = "You have turned on the airplane mode."
                pleaseTurnOffText = "Please turn off"
                airplaneModeOffButtonText = "Airplane mode"
                showAirplaneModeOffButtons = true
            }
        }.build()
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
                    productList.clear()
                    productList.addAll(data)
                    prductAdapter = ProductAdapter(baseContext, data)
                    rvView.adapter = prductAdapter
                }
            }

            override fun onFailure(call: Call<List<ProductItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun filterProducts(query: String) {
        val filteredList = productList.filter { product ->
            product.product_name.contains(query, true) || product.product_type.contains(query, true)
        }
        prductAdapter.updateList(filteredList)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
