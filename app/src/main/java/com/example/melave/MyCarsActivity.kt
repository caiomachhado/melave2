package com.example.melave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyCarsActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Car>
    private lateinit var CarAdapter: RecyclerView

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "MyCarsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cars)

        userRecyclerView = findViewById(R.id.carList)
        userRecyclerView.LayoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getCarData()

        initialise()

    }

    private fun getCarData() {

        dbref = FirebaseDatabase.getInstance().getReference("Cars").child("cars")
        dbref.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (carSnapshot in snapshot.children){

                        val car = carSnapshot.getValue(Car::class.java)
                        userArrayList.add(car!!)

                    }

                    userRecyclerView.adapter = CarAdapter(userArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}