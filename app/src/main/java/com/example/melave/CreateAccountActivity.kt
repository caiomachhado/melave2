@file:Suppress("DEPRECATION")

package com.example.melave

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class CreateAccountActivity() : AppCompatActivity() {

    private var edit_text_usuario: EditText? = null
    private var edit_text_email: EditText? = null
    private var edit_text_senha: EditText? = null
    private var edit_text_reSenha: EditText? = null
    private var btn_create: Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    private var usuario: String? = null
    private var email: String? = null
    private var senha: String? = null
    private var reSenha: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()
    }

    private fun initialise() {
        edit_text_usuario = findViewById<EditText>(R.id.edit_text_usuario)
        edit_text_email = findViewById<EditText>(R.id.edit_text_email)
        edit_text_senha = findViewById<EditText>(R.id.edit_text_senha)
        edit_text_reSenha = findViewById<EditText>(R.id.edit_text_reSenha)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btn_create!!.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount() {
        usuario = edit_text_usuario?.text.toString()
        email = edit_text_email?.text.toString()
        senha = edit_text_senha?.text.toString()
        reSenha = edit_text_reSenha?.text.toString()

        if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(reSenha)) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

        mProgressBar!!.setMessage("Registrando Usuario...")
        mProgressBar!!.show()

        mAuth!!.createUserWithEmailAndPassword(email!!, senha!!).addOnCompleteListener(this) { task ->
            mProgressBar!!.hide()

            if (task.isSuccessful) {
                Log.d(TAG, "CreateUserWithEmail:Sucess")

                val userId = mAuth!!.currentUser!!.uid

                verifyEmail();

                val currentUserDb = mDatabaseReference!!.child(userId)
                currentUserDb.child("usuario").setValue(usuario)
                currentUserDb.child("email").setValue(email)

                updateUserInfoandUi();

            } else {

                Log.w(TAG, "CreateUserWithEmail:Failure", task.exception)
                Toast.makeText(this@CreateAccountActivity, "A autenticação falhou", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification().addOnCompleteListener(this){
            task ->

            if(task.isSuccessful){
                Toast.makeText(this@CreateAccountActivity, "Email de verificação enviado para" + mUser.email,
                        Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@CreateAccountActivity, "Erro ao enviar email de verificação.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInfoandUi() {
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}




