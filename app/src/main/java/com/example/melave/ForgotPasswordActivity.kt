package com.example.melave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG = "ForgotPasswordActivity"

    private var edit_text_email_forgotPass: EditText? = null
    private var btn_forgotPass: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initialise()

    }

    private fun initialise() {
        edit_text_email_forgotPass = findViewById(R.id.edit_text_email_forgotPass)
        btn_forgotPass = findViewById(R.id.btn_forgotPass)

        mAuth = FirebaseAuth.getInstance()

        btn_forgotPass!!.setOnClickListener { sendPasswordEmail() }

    }

    private fun sendPasswordEmail() {
       val email = edit_text_email_forgotPass?.text.toString()

        if (!TextUtils.isEmpty(email)){
            mAuth!!
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            val message = "Email Enviado."
                            Log.d(TAG, message)
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            updateUI()
                        } else {
                            Log.w(TAG, task.exception!!.message)
                            Toast.makeText(this, "Nenhum usuário com esse e-mail", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

}