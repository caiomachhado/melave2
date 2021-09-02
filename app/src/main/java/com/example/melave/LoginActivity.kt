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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

            mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){ task ->

                mProgressBar!!.hide()

                if (task.isSuccessful){
                    Log.d(TAG, "Logado com sucesso")
                    val rootRef = FirebaseDatabase.getInstance().reference
                    val messageRef = rootRef.child("users")
                    val valueEventListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (ds in dataSnapshot.children) {
                                val check = ds.child("adminOrUser").getValue(String::class.java)
                                Log.d("TAG",  check + " " )

                                if (check == "User"){
                                    updateUiUser()
                                } else {
                                    updateUi()
                                }

                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d("TAG", databaseError.getMessage()) //Don't ignore errors!
                        }
                    }
                    messageRef.addListenerForSingleValueEvent(valueEventListener)

                } else {

                    Log.e(TAG, "Erro ao logar.", task.exception)
                    Toast.makeText(this@LoginActivity, "Autenticação com Falhas", Toast.LENGTH_SHORT).show()

                }

            }

        } else {
            Toast.makeText(this@LoginActivity, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUiUser() {

        var currentuser = FirebaseAuth.getInstance().currentUser?.uid;


        val intent = Intent(this@LoginActivity, FeedActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun updateUi() {

        var currentuser = FirebaseAuth.getInstance().currentUser?.uid;


        val intent = Intent(this@LoginActivity, FeedActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}