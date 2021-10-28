package com.example.melave

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCarActivity : AppCompatActivity() {

    private var edit_text_add_car_brand: EditText? = null
    private var edit_text_add_car_model: EditText? = null
    private var edit_text_add_car_color: EditText? = null
    private var edit_text_add_carNumber: EditText? = null
    private var btn_addCar: Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "AddCarActivity"

    private var carBrand: String? = null
    private var carModel: String? = null
    private var carColor: String? = null
    private var carNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        initialise()

    }

    private fun initialise() {

        edit_text_add_car_brand = findViewById(R.id.edit_text_add_car_brand)
        edit_text_add_car_model = findViewById(R.id.edit_text_add_car_model)
        edit_text_add_car_color = findViewById(R.id.edit_text_add_car_color)
        edit_text_add_carNumber = findViewById(R.id.edit_text_add_carNumber)
        btn_addCar = findViewById(R.id.btn_addCar)


        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("")
        mAuth = FirebaseAuth.getInstance()

        btn_addCar?.setOnClickListener { addCar() }

    }

    private fun addCar() {

        carBrand = edit_text_add_car_brand?.text.toString()
        carModel = edit_text_add_car_model?.text.toString()
        carColor = edit_text_add_car_color?.text.toString()
        carNumber = edit_text_add_carNumber?.text.toString()

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)

        val carsRef =  mDatabaseReference!!.child("Users").child(userId).child("Cars").child("")
        val key = carsRef.push().key
        if (key != null) {
            carsRef.child(key).child("owner").setValue(userId)
            carsRef.child(key).child("carBrand").setValue(carBrand)
            carsRef.child(key).child("carModel").setValue(carModel)
            carsRef.child(key).child("carColor").setValue(carColor)
            carsRef.child(key).child("carNumber").setValue(carNumber)
            carsRef.child(key).child("carSituation").setValue("Na Garagem")
        }

        updateUserCars()

    }

    private fun updateUserCars() {
        val intent = Intent(this@AddCarActivity, MyCarsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)
    }
}
