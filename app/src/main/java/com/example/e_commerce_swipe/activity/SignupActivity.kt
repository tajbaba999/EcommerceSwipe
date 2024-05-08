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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


class SignupActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 9001
    val Req_Code: Int = 123
    private lateinit var binding : ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var client: GoogleSignInClient
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

        binding.buttonGoogleSignUp.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            client = GoogleSignIn.getClient(this, options)
            startActivityForResult(client.signInIntent, RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) { // Use the same request code as RC_SIGN_IN
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                Log.w("SignupActivity", "Google sign in failed", e)
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