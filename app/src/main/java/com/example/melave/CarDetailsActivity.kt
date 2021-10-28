package com.example.melave


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable


class CarDetailsActivity : AppCompatActivity() {

    private var details_car_number: TextView? = null
    private var details_car_brand: TextView? = null
    private var details_car_color: TextView? = null
    private var details_car_model: TextView? = null
    private var details_car_situation: TextView? = null
    private var schedule_button: TextView? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        initialise()

    }

    private fun initialise() {
        details_car_brand = findViewById(R.id.details_car_brand)
        details_car_model = findViewById(R.id.details_car_model)
        details_car_color = findViewById(R.id.details_car_color)
        details_car_number = findViewById(R.id.details_car_number)
        details_car_situation = findViewById(R.id.details_car_situation)
        schedule_button = findViewById(R.id.schedule_button)

        val car = intent.getSerializableExtra("carDetails") as Car

        details_car_brand?.text = car.carBrand
        details_car_model?.text = car.carModel
        details_car_color?.text = car.carColor
        details_car_number?.text = car.carNumber
        details_car_situation?.text = car.carSituation

        val carNumber = car.carNumber.toString()
        val carBrand = car.carBrand.toString()
        val carModel = car.carModel.toString()
        val carColor = car.carColor.toString()
        val carSituation = car.carSituation.toString()

        schedule_button?.setOnClickListener{

         val intent = Intent(
             it.context, ScheduleWash::class.java
         )
            intent.putExtra("carNumber", carNumber)
            intent.putExtra("carBrand", carBrand)
            intent.putExtra("carModel", carModel)
            intent.putExtra("carColor", carColor)
            intent.putExtra("carSituation", carSituation)
            it.context.startActivity(intent)

        }

    }


}