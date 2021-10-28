package com.example.melave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ScheduleWash : AppCompatActivity() {

    private var car_number_select : TextView? = null
    private var car_brand_select : TextView? = null
    private var car_model_select : TextView? = null
    private var car_color_select : TextView? = null
    private var button4 : Button? = null

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Washer>

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_wash)

        userRecyclerView = findViewById(R.id.schedule_recycler)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
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

        car_number_select = findViewById(R.id.car_number_selected)
        car_color_select = findViewById(R.id.car_color_selected)
        car_model_select = findViewById(R.id.car_model_selected)
        car_brand_select = findViewById(R.id.car_brand_selected)
        button4 = findViewById(R.id.btn_schedule)

        val carNumber = intent.getSerializableExtra("carNumber")
        val carBrand = intent.getSerializableExtra("carBrand")
        val carModel = intent.getSerializableExtra("carModel")
        val carColor = intent.getSerializableExtra("carColor")
        val carSituation = intent.getSerializableExtra("carSituation")

        car_number_select?.text = carNumber.toString()
        car_brand_select?.text = carBrand.toString()
        car_model_select?.text = carModel.toString()
        car_color_select?.text = carColor.toString()


        button4?.setOnClickListener { startActivity(Intent(this@ScheduleWash, MyCarsActivity::class.java))

            Toast.makeText(this@ScheduleWash, "Solicitação enviada ao lavador", Toast.LENGTH_SHORT).show()
        }

    }

}
