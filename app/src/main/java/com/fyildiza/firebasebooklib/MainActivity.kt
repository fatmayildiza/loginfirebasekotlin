package com.fyildiza.firebasebooklib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var btnSignOut:Button
    private lateinit var mAuth:FirebaseAuth

    private lateinit var loginBtnID:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignOut=findViewById(R.id.btnSignOut)
        mAuth= FirebaseAuth.getInstance()

        btnSignOut.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(this,"Çıkoş başarılı",Toast.LENGTH_SHORT).show()
            finish()
        }

        loginBtnID=findViewById(R.id.buttonlogin)
        loginBtnID.setOnClickListener{
            val intent = Intent(this, SignUpSignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}