package com.example.messenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private lateinit var auth: FirebaseAuth
val TAG = "RegistrationActivity"
var selectedPhotoUri: Uri? = null

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        // Register the user
        registerButton.setOnClickListener {
            performRegistration()
        }

        // Launch login activity
        loginPrompt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                selectedPhotoUri = result.data?.data

                Log.d(TAG, selectedPhotoUri.toString())

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

                profile_image.setImageBitmap(bitmap)
                selectPhotoBtn.alpha = 0f
//                val bitmapDrawable = BitmapDrawable(bitmap)
//                selectPhotoBtn.setBackgroundDrawable(bitmapDrawable)
            }
        }
        selectPhotoBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(applicationContext, "User loged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performRegistration() {
        val userName = userName.text.toString()
        val userEmail = userEmail.text.toString()
        val userPassword = userPassword.text.toString()

        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(baseContext, "Fill all the fields",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createdUserWithEmailAndPassword")
                    val user = auth.currentUser

                    uploadUserImage()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun uploadUserImage() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register", "Successfully uploaded image: ")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    saveUserToFirebaseDB(it.toString())
                }
            }
    }
    private fun saveUserToFirebaseDB(profileImgUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, userName.text.toString(), profileImgUrl )
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "User have been saved")
                val intent = Intent(this, LatestMessages::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }
}

class User(val uid: String, val username: String, val profileImgUrl: String) {
    constructor() : this("", "", "")
}