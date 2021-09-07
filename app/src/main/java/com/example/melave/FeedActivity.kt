package com.example.melave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "FeedActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        initialise()

    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        mAuth = FirebaseAuth.getInstance()

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)

        val currentUserDb = mDatabaseReference!!.child(userId)
        val nameWasher = currentUserDb.child("nameLavador").get().addOnSuccessListener {
            Log.d("TAG", "ENCONTRADO ${it.value}")
            val nameSetWasher = it.value



        }


    }

}