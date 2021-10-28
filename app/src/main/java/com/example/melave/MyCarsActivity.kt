package com.example.melave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyCarsActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var carsRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cars)

        carsRecyclerView = findViewById(R.id.carsList)
        carsRecyclerView.layoutManager = LinearLayoutManager(this)
        carsRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getCarData()

    }

    private fun getCarData() {

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("CAIO", "ID do Usuário: $userId")

        dbref = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Cars")


        dbref
            .addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                        for (carSnapshot in snapshot.children) {
                            val car = carSnapshot.getValue(Car::class.java)
                            userArrayList.add(car!!)


                        }
                        carsRecyclerView.adapter = CarAdapter(userArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}