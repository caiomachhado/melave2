package com.example.melave

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity() {

    private var edit_text_usuario: EditText? = null
    private var edit_text_email: EditText? = null
    private var edit_text_senha: EditText? = null
    private var btn_createAcc: Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    private var usuario: String? = null
    private var email: String? = null
    private var senha: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise();
    }

    private fun initialise() {
        edit_text_usuario = findViewById(R.id.edit_text_createAcc_usuario_)
        edit_text_email = findViewById(R.id.edit_text_createAcc_email)
        edit_text_senha = findViewById(R.id.edit_text_createAcc_password)
        btn_createAcc = findViewById(R.id.btn_createAcc)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btn_createAcc?.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount() {
        usuario = edit_text_usuario?.text.toString()
        email = edit_text_email?.text.toString()
        senha = edit_text_senha?.text.toString()

        if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

        mProgressBar?.setMessage("Registrando Usuario...")
        mProgressBar?.show()

        mAuth?.let {

            it.createUserWithEmailAndPassword(email!!, senha!!).addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Log.d(TAG, "CreateUserWithEmail:Sucess")

                    val userId = it.currentUser?.uid

                    verifyEmail()

                    val currentUserDb = userId?.let { it1 -> mDatabaseReference!!.child(it1) }
                    currentUserDb?.child("usuario")?.setValue(usuario)
                    currentUserDb?.child("adminOrUser")?.setValue("User")

                    updateUserInfoandUi()

                } else {

                    Log.w(TAG, "CreateUserWithEmail:Failure", task.exception)
                    Toast.makeText(this@CreateAccountActivity, "A autenticação falhou", Toast.LENGTH_SHORT).show()

                }
            }

        }



    }

    private fun verifyEmail() {
        val mUser = mAuth?.currentUser;
        mUser?.sendEmailVerification()?.addOnCompleteListener(this){
            task ->

            if(task.isSuccessful){
                Toast.makeText(this@CreateAccountActivity, "Email de verificação enviado para " + mUser.email,
                        Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@CreateAccountActivity, "Erro ao enviar email de verificação.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInfoandUi() {
        val intent = Intent(this@CreateAccountActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}




