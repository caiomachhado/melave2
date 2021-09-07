package com.example.melave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FeedActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Washer>
    private lateinit var washerAdapter: RecyclerView

    private var text_view_client_name: TextView? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "FeedActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        userRecyclerView = findViewById(R.id.washerList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<Washer>()
        getUserData()


        initialise()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Washer")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (washerSnapshot in snapshot.children){

                        val washer = washerSnapshot.getValue(Washer::class.java)
                        userArrayList.add(washer!!)

                    }

                    userRecyclerView.adapter = WasherAdapter(userArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        mAuth = FirebaseAuth.getInstance()

        text_view_client_name = findViewById(R.id.text_view_client_name)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)

        val currentUserDb = mDatabaseReference!!.child(userId)
        val nameWasher = currentUserDb.child("clientName").get().addOnSuccessListener {
            Log.d("TAG", "ENCONTRADO ${it.value}")
            val nameSetWasher = it.value
                text_view_client_name?.text = nameSetWasher as String?
            }

        }
}

