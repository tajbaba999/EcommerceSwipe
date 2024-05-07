package com.example.e_commerce_swipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_commerce_swipe.R
import com.example.e_commerce_swipe.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.example.e_commerce_swipe.MainActivity
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Thread.sleep(1000)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textViewAccountexist.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCompat.setOnClickListener {
            val email = binding.textinputemail.text.toString()
            val pass = binding.inputpassword.text.toString()
            val confpass = binding.inputconfirm.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty()) {
                if (pass == confpass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                val errorMessage = task.exception?.message
                                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                                Log.e("SignupActivity", "Error creating user: $errorMessage")

                            }
                        }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}