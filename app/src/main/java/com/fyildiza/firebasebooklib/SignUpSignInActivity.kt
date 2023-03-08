package com.fyildiza.firebasebooklib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpSignInActivity : AppCompatActivity() {
    private lateinit var edtEmailID: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnSignIn: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_sign_in)
        edtEmailID= findViewById(R.id.edtEmailID)
        edtPassword=findViewById(R.id.edtPassword)
        btnSignUp=findViewById(R.id.btnSignUp)
        btnSignIn= findViewById(R.id.btnSignIn)

        mAuth= FirebaseAuth.getInstance()


        btnSignUp.setOnClickListener {
            val emailID = edtEmailID.text.toString()
            val password = edtPassword.text.toString()
            lifecycleScope.launch(Dispatchers.IO){
                signUpWithEmailAndPassword(mAuth,emailID,password)
            }

        }

        btnSignIn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val emailID = edtEmailID.text.toString()
            val password = edtPassword.text.toString()
            lifecycleScope.launch(Dispatchers.IO){
                signInWithEmailAndPassword(mAuth,emailID,password)
            }
        }

    }

    suspend fun signUpWithEmailAndPassword(firebaseAuth: FirebaseAuth, emailID:String, password:String): AuthResult?{
        return try {
            val result=firebaseAuth.createUserWithEmailAndPassword(emailID,password).await()
            updateUI(result.user )
             result
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@SignUpSignInActivity,"${e.message}",Toast.LENGTH_SHORT).show()
                Log.d("AuthResult","${e.message}")
                progressBar.visibility= View.GONE
            }
            null

        }


    }

    suspend fun signInWithEmailAndPassword(firebaseAuth: FirebaseAuth, emailID:String, password:String): AuthResult?{
        return try {
            val result=firebaseAuth.signInWithEmailAndPassword(emailID,password).await()
            updateUI(result.user )
            result
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@SignUpSignInActivity,"${e.message}",Toast.LENGTH_SHORT).show()
                Log.d("AuthResult","${e.message}")
                progressBar.visibility= View.GONE
            }
            null

        }

    }


    private suspend fun  updateUI(firebaseUser: FirebaseUser?) {


        Log.d("AuthResult","${firebaseUser?.email}")

        withContext(Dispatchers.Main) {
            progressBar.visibility=View.GONE
            Toast.makeText(this@SignUpSignInActivity,"GİRİŞ BAŞARILI",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignUpSignInActivity,MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this@SignUpSignInActivity,MainActivity::class.java))
            finish()
        }
    }
}