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

    private val TAG = "LavadorCreateAdress"

    private var washerAdress: String? = null
    private var washerComplete: String? = null
    private var washerMedium: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lavador_create_adress)

        initialise();

    }

    private fun initialise() {
        edit_text_adress = findViewById(R.id.edit_text_createLavador_adress)
        edit_text_washerComplete = findViewById(R.id.edit_text_createLavador_washComplete)
        edit_text_washerMedium = findViewById(R.id.edit_text_createLavador_washMedium)
        btn_createWasherAdress = findViewById(R.id.btn_createWasherAdress)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Cars")
        mAuth = FirebaseAuth.getInstance()

        btn_createWasherAdress?.setOnClickListener { createWasherAdress() }

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
        currentUserDb.child("priceWashComplete").setValue(washerComplete)
        currentUserDb.child("priceWashMedium").setValue(washerMedium)

        updateUserAdress()


    }

    private fun updateUserAdress() {
        val intent = Intent(this@LavadorCreateAdress, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)
    }
}

