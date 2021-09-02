package com.example.melave

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null


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

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        mAuth = FirebaseAuth.getInstance()

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!.setOnClickListener{ startActivity(
            Intent(
                this@LoginActivity,
                ForgotPasswordActivity::class.java
            )
        )}

        btn_create_account!!.setOnClickListener{ startActivity(
            Intent(
                this@LoginActivity,
                CreateAccountActivity::class.java
            )
        )}

        btn_login!!.setOnClickListener { loginUser() }

    }

    private fun loginUser() {
        email = edit_text_login_email?.text.toString()
        password = edit_text_password_login?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgressBar!!.setMessage("Verificando Usuário")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuário")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){ task ->

                mProgressBar!!.hide()

                if (task.isSuccessful){
                    Log.d(TAG, "Logado com sucesso")
                    updateUi()

                } else {

                    Log.e(TAG, "Erro ao logar.", task.exception)
                    Toast.makeText(
                        this@LoginActivity,
                        "Autenticação com Falhas",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        } else {
            Toast.makeText(this@LoginActivity, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUiUser() {

        val intent = Intent(this@LoginActivity, LavadorFeedActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun updateUi() {

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)

        val currentUserDb =  mDatabaseReference!!.child(userId)
        val adminOrUser = currentUserDb.child("adminOrUser").get().addOnSuccessListener {
            Log.d("TAG", "ENCONTRADO ${it.value}")
            val isOrNot = it.value

            if (isOrNot?.equals("User") == true){
                val intent = Intent(this@LoginActivity, FeedActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                val intent = Intent(this@LoginActivity, LavadorFeedActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }

        } .addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }



    }
}