package com.example.melave

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LavadorCreateAccount : AppCompatActivity() {
    private var edit_text_usuario: EditText? = null
    private var edit_text_name: EditText? = null
    /*private var edit_text_adress: EditText? = null
    private var edit_text_washerComplete: EditText? = null
    private var edit_text_washerMedium: EditText? = null*/
    private var edit_text_email: EditText? = null
    private var edit_text_senha: EditText? = null
    private var btn_createLavador: Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "LavadorCreateAccount"

    private var usuario: String? = null
    private var name: String? = null
    /*private var washerAdress: String? = null
    private var washerComplete: String? = null
    private var washerMedium: String? = null*/
    private var email: String? = null
    private var senha: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lavador_create_account)

        initialise();
    }

    private fun initialise() {
        edit_text_usuario = findViewById(R.id.edit_text_createLavador_usuario)
        edit_text_name = findViewById(R.id.edit_text_createLavador_name)
        /*edit_text_adress = findViewById(R.id.edit_text_createLavador_adress)
        edit_text_washerComplete = findViewById(R.id.edit_text_createLavador_washComplete)
        edit_text_washerMedium = findViewById(R.id.edit_text_createLavador_washMedium)*/
        edit_text_email = findViewById(R.id.edit_text_createLavador_email)
        edit_text_senha = findViewById(R.id.edit_text_createLavador_password)
        btn_createLavador = findViewById(R.id.btn_createWasherAdress)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Washer")
        mAuth = FirebaseAuth.getInstance()

        btn_createLavador?.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount() {
        usuario = edit_text_usuario?.text.toString()
        name = edit_text_name?.text.toString()
        /*washerAdress = edit_text_adress?.text.toString()
        washerComplete = edit_text_washerComplete?.text.toString()
        washerMedium = edit_text_washerMedium?.text.toString()*/
        email = edit_text_email?.text.toString()
        senha = edit_text_senha?.text.toString()

        if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

        mProgressBar?.setMessage("Registrando Lavador...")
        mProgressBar?.show()


        mAuth!!.createUserWithEmailAndPassword(email!!, senha!!).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                Log.d(TAG, "CreateUserWithEmail:Sucess")

                val userId = mAuth!!.currentUser!!.uid

                verifyEmail()

                val currentUserDb =  mDatabaseReference!!.child(userId)
                currentUserDb.child("usuario").setValue(usuario)
                currentUserDb.child("nameLavador").setValue(name)
                /*currentUserDb.child("washerAdress").setValue(washerAdress)
                currentUserDb.child("priceWashComplete").setValue(washerComplete)
                currentUserDb.child("priceWashMedium").setValue(washerMedium)*/
                currentUserDb.child("adminOrUser").setValue("Lavador")

                updateUserInfoandUi()

            } else {

                Log.w(TAG, "CreateUserWithEmail:Failure", task.exception)
                Toast.makeText(this@LavadorCreateAccount, "A autenticação falhou", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun verifyEmail() {
        val mUser = mAuth?.currentUser;
        mUser?.sendEmailVerification()?.addOnCompleteListener(this){
                task ->

            if(task.isSuccessful){
                Toast.makeText(this@LavadorCreateAccount, "Email de verificação enviado para " + mUser.email,
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@LavadorCreateAccount, "Erro ao enviar email de verificação.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInfoandUi() {
        val intent = Intent(this@LavadorCreateAccount, LavadorCreateAdress::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = currentFirebaseUser!!.uid
        Log.d("TAG", userId)



    }
}
