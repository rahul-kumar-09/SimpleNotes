package com.example.simplenoteapp.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simplenoteapp.R
import com.example.simplenoteapp.activity.MainActivity
import com.example.simplenoteapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }


        binding.login.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            if (email.isEmpty()){
                binding.etEmail.setError("Please fill the Email")
            }
            if (pass.isEmpty()){
                binding.etPassword.setError("Please fill the Password")
            }

            if (email.isNotEmpty() && pass.isNotEmpty()){

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this) {task->
                    if (task.isSuccessful){
                        updateUI()
                    } else {
                        Toast.makeText(this, "Please Signup the login", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty field not allows", Toast.LENGTH_SHORT).show()
            }

        }

    }


    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}