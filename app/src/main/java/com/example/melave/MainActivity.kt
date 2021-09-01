package com.example.melave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {

    private var btn_login: Button? = null
    private var btn_newLavador: Button? = null
    private var btn_newCliente: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun initialise() {

        btn_login = findViewById<Button>(R.id.btn_login)
        btn_newLavador = findViewById<Button>(R.id.btn_newLavador)
        btn_newCliente = findViewById<Button>(R.id.btn_newClient)

        btn_login!!.setOnClickListener { startActivity(Intent(this@MainActivity, LoginActivity::class.java)) }
        btn_newLavador!!.setOnClickListener { startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java)) }
        btn_newCliente!!.setOnClickListener { startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java)) }

    }




    

}