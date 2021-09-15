package com.example.melave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LavadorCreateAdress : AppCompatActivity() {

    private var edit_text_adress: EditText? = null
    private var edit_text_washerComplete: EditText? = null
    private var edit_text_washerMedium: EditText? = null
    private var btn_createWasherAdress: Button? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "LavadorCreateAccount"

    private var washerAdress: String? = null
    private var washerComplete: String? = null
    private var washerMedium: String? = null
    private var btnCreateWasherAdress: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lavador_create_adress)

        initialise();

    }

    private fun initialise() {
        edit_text_adress = findViewById(R.id.edit_text_createLavador_adress)
        edit_text_washerComplete = findViewById(R.id.edit_text_createLavador_washComplete)
        edit_text_washerMedium = findViewById(R.id.edit_text_createLavador_washMedium)
        btnCreateWasherAdress = findViewById(R.id.btn_createWasherAdress)

        btnCreateWasherAdress?.setOnClickListener { createWasherAdress() }


    }

    private fun createWasherAdress() {
        washerAdress = edit_text_adress?.text.toString()
        washerComplete = edit_text_washerComplete?.text.toString()
        washerMedium = edit_text_washerMedium?.text.toString()

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)

        val currentUserDb =  mDatabaseReference!!.child(userId)
        currentUserDb.child("washerAdress").setValue(washerAdress)
        currentUserDb.child("washerComplete").setValue(washerComplete)
        currentUserDb.child("washerMedium").setValue(washerMedium)

        updateUserInfoandUi()


    }

    private fun updateUserInfoandUi() {
        val intent = Intent(this@LavadorCreateAdress, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)
    }
}

