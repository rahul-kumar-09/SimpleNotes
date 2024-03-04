package com.example.simplenoteapp.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.simplenoteapp.R
import com.example.simplenoteapp.activity.MainActivity
import com.example.simplenoteapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)




        setLogOutBtn()
        setInProgress(true)


    }


    private fun setLogOutBtn() {
        database = Firebase.database.reference

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("User").child(userId).get().addOnSuccessListener {
            setInProgress(false)
            val name = it.child("name").value.toString()
            val email = it.child("email").value.toString()
            val address = it.child("address").value.toString()

            binding.dName.text = name
            binding.dEmail.text = email
            binding.dAddress.text = address

        }.addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.btnLogOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }


    private fun setInProgress(inProgress: Boolean){
        if (inProgress){
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogOut.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogOut.visibility = View.VISIBLE
        }
    }

}