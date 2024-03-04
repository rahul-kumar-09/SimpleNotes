package com.example.simplenoteapp.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simplenoteapp.R
import com.example.simplenoteapp.activity.MainActivity
import com.example.simplenoteapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        database = Firebase.database.reference

        binding.btnLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


        binding.btnRegister.setOnClickListener {
            val email = binding.tvEmail.text.toString().trim()
            val pass = binding.tvPassword.text.toString().trim()
            val address = binding.tvAddress.text.toString().trim()
            val name = binding.tvName.text.toString().trim()
            val conPass = binding.tvConfirmPassword.text.toString().trim()


            if (name.isEmpty()){
                binding.tvName.setError("Please fill the Name")
            }

            if (email.isEmpty()){
                binding.tvEmail.setError("Please fill the Email")
            }

            if (address.isEmpty()){
                binding.tvAddress.setError("Please fill the Address")
            }

            if (pass.isEmpty()){
                binding.tvPassword.setError("Please set a Password")
            }
            if (conPass.isEmpty()){
                binding.tvConfirmPassword.setError("Please fill the Password")
            }

            if (pass != conPass){
                binding.tvConfirmPassword.setError("Password not match ")
            }


            if (email.isNotEmpty() && pass.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this) {task->
                    if (task.isSuccessful){
                        saveData()
                        updateUI()
                    } else {
                        Toast.makeText(this, "This email is already used", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty field not allows", Toast.LENGTH_SHORT).show()
            }

        }



    }


    private fun saveData() {
        val email = binding.tvEmail.text.toString().trim()
        val name = binding.tvName.text.toString().trim()
        val pass = binding.tvPassword.text.toString().trim()
        val address = binding.tvAddress.text.toString().trim()

        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        val user = UserModel(email, address, name,pass)

        database.child("User").child(userID).setValue(user)

    }

    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            updateUI()
        }
    }

}