package com.example.e_commerce_swipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_commerce_swipe.databinding.ActivityProductaddBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class Productadd : AppCompatActivity() {
    private lateinit var binding: ActivityProductaddBinding
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            binding.selectimage.setImageURI(data?.data)
        }
    }
}