package com.example.websmk

import android.app.DownloadManager.Request
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.websmk.retrofit.Retro
import com.example.websmk.retrofit.UserRequest
import com.example.websmk.retrofit.UserResponse
import com.example.websmk.retrofit.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Login : AppCompatActivity() {

    val PREF_NAME = "MyAppPrefs"
    val username1 : EditText = findViewById(R.id.ed_username)
    val password1 : EditText = findViewById(R.id.ed_password)
    val tmblogin:Button = findViewById(R.id.btnlogin)
    val tmbsignup:Button = findViewById(R.id.btnsignup)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //cek apakah pengguna sudah login
        if (isLogedIn()){
            //jika sudah login langsung ke mainactivity
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            finish()
        }

        initAction()
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

    fun initAction(){
        tmblogin.setOnClickListener{
            login()
        }
        tmbsignup.setOnClickListener {
            Toast.makeText(applicationContext,"Nanti dibuatkan dulu registernya",Toast.LENGTH_SHORT).show()
        }

    }

    fun login(){
        val request : UserRequest()
        request.username = username1.text.toString().trim()
        request.password = password1.text.toString().trim()

        val retro = Retro().getRetroClientInstance().create(apiService::class.java)
        retro.login(request).enqueue(object :Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val petugas = response.body()
                Log.e("username",petugas!!.data?.username)
                Log.e("username",petugas!!.data?.nama)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("errot", t.message.toString())
            }

        })


    }
}