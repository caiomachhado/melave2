package com.example.melave

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private var email: String? = null
    private var password: String? = null

    private var edit_text_login_email: EditText? = null
    private var edit_text_password_login: EditText? = null
    private var btn_login: TextView? = null
    private var tvForgotPassword: TextView? = null
    private var btn_create_account: TextView? = null
    private var mProgressBar: ProgressDialog? = null

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialise();

    }

    private fun initialise() {
        tvForgotPassword = findViewById<TextView>(R.id.edit_text_forgotPass)
        edit_text_login_email = findViewById<EditText>(R.id.edit_text_usuario_login)
        edit_text_password_login = findViewById<EditText>(R.id.edit_text_password_login)
        btn_login = findViewById<Button>(R.id.btn_create)
        btn_create_account = findViewById<Button>(R.id.btn_create_account)
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!.setOnClickListener{ startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))}

        btn_create_account!!.setOnClickListener{ startActivity(Intent(this@LoginActivity, CreateAccountActivity::class.java))}

        btn_login!!.setOnClickListener { loginUser() }

    }

    private fun loginUser() {
        email = edit_text_login_email?.text.toString()
        password = edit_text_password_login?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgressBar!!.setMessage("Verificando Usuário")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuário")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){
                task ->

                mProgressBar!!.hide()

                if (task.isSuccessful){
                    Log.d(TAG,  "Logado com sucesso")
                    updateUi()
                } else {

                    Log.e(TAG, "Erro ao logar.",task.exception)
                    Toast.makeText(this@LoginActivity, "Autenticação com Falhas", Toast.LENGTH_SHORT).show()

                }

            }

        } else {
            Toast.makeText(this@LoginActivity, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUi() {
        val intent = Intent(this@LoginActivity, FeedActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}