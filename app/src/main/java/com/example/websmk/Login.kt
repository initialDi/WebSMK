package com.example.websmk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {

    val PREF_NAME = "MyAppPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username1 : EditText = findViewById(R.id.username)
        val password1 : EditText = findViewById(R.id.password)
        val tmblogin:Button = findViewById(R.id.login)

        //cek apakah pengguna sudah login
        if (isLogedIn()){
            //jika sudah login langsung ke mainactivity
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }

//        //listener untuk tombol login
        tmblogin.setOnClickListener{
            val usnm = username1.text.toString()
            val pswd = password1.text.toString()

            if(usnm == "rinaldi" && pswd == "admin"){
                saveSesi(usnm)

                startActivity(Intent(this, MainActivity::class.java))
            }
            else{
                Toast.makeText(applicationContext,"Login Gagal",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveSesi(usnm : String){
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("usnm", usnm)
        editor.putBoolean("isLogedIn", true)
        editor.apply()
    }

    private fun isLogedIn(): Boolean{
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLogedIn", false)
    }
}