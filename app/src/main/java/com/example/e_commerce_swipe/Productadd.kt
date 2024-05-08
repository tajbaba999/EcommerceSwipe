package com.example.e_commerce_swipe
import com.github.dhaval2404.imagepicker.util.FileUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_swipe.databinding.ActivityProductaddBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Productadd : AppCompatActivity() {
    private lateinit var binding: ActivityProductaddBinding
    private val BASE_URL = "https://app.getswipe.in/api/public/"
    private val apiService = RetrofitClient.getClient(BASE_URL).create(ProductpostService::class.java)
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectimagebtn.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.buttonupload.setOnClickListener {
            val name = binding.texfiledproductname.text.toString()
            val category = binding.texfiledcatogery.text.toString()
            val price = binding.texfiledPrice.text.toString()
            val tax = binding.texfiledTax.text.toString()

                addProduct(name, category, price, tax)

        }
    }

    private fun addProduct(name: String, category: String, price: String, tax: String) {
//        val imageFilePath = FileUtil.getPath(this, imageUri!!)
//        val imageFile = File(imageFilePath)
//        val imageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
//        val imagePart = MultipartBody.Part.createFormData("files[]", imageFile.name, imageRequestBody)


        val call = apiService.addProduct(name, category, price, tax)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null) {
                        Toast.makeText(this@Productadd, "${productResponse.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Productadd, "Try again", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@Productadd, "${t.toString()}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            imageUri = data?.data
            binding.selectimage.setImageURI(imageUri)
        }
    }
}
