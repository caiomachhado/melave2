package com.example.melave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class WasherDetails : AppCompatActivity() {

    private var washerDetails_name: TextView? = null
    private var washerDetails_adress: TextView? = null
    private var washerDetails_complete: TextView? = null
    private var washerDetails_medium: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_washer_details)

        initialise()
    }

    private fun initialise() {
        washerDetails_name = findViewById(R.id.washerDetails_name)
        washerDetails_adress = findViewById(R.id.washerDetails_adress)
        washerDetails_complete = findViewById(R.id.washerDetails_complete)
        washerDetails_medium = findViewById(R.id.washerDetails_medium)

        val washer = intent.getSerializableExtra("washerDetails") as Washer

        washerDetails_name?.text = washer.nameLavador
        washerDetails_adress?.text = washer.washerAdress
        washerDetails_complete?.text = washer.priceWashComplete
        washerDetails_medium?.text = washer.priceWashMedium

    }
}