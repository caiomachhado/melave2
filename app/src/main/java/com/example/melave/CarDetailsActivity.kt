package com.example.melave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import org.w3c.dom.Text

class CarDetailsActivity : AppCompatActivity() {

    private var details_car_number: TextView? = null
    private var details_car_brand: TextView? = null
    private var details_car_color: TextView? = null
    private var details_car_model: TextView? = null

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

        val car = intent.getSerializableExtra("carDetails") as Car

        details_car_brand?.text = car.carBrand
        details_car_model?.text = car.carModel
        details_car_color?.text = car.carColor
        details_car_number?.text = car.carNumber

    }

}