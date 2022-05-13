package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TAG = "MainActivity"

        val userName = userName.text.toString()
        val userEmail = userEmail.text.toString()
        val userPassword = userPassword.text.toString()

        // Register the user
        registerButton.setOnClickListener {

        }

        // Launch login activity
        loginPrompt.setOnClickListener {

        }

    }
}