package com.example.websmk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.websmk.retrofit.Retro
import com.example.websmk.retrofit.UserResponse
import com.example.websmk.retrofit.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.websmk.retrofit.UserRequest

class Login : AppCompatActivity() {

    val PREF_NAME = "MyAppPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username1 : EditText = findViewById(R.id.ed_username)
        val password1 : EditText = findViewById(R.id.ed_password)
        val tmblogin:Button = findViewById(R.id.btnlogin)
        val tmbsignup:Button = findViewById(R.id.btnsignup)

        //cek apakah pengguna sudah login
        if (isLogedIn()){
            //jika sudah login langsung ke mainactivity
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            finish()
        }

        tmblogin.setOnClickListener{

            val request = UserRequest()
            request.username = username1.text.toString().trim()
            request.password = password1.text.toString().trim()

            val retro = Retro().getRetroClientInstance().create(apiService::class.java)
            retro.login(request).enqueue(object :Callback<UserResponse>{
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {


                    val petugas = response.body()

                    if (petugas!=null) {
                        Log.e("username", petugas!!.data?.username.toString())
                        Log.e("nama", petugas!!.data?.nama.toString())

                        // Simpan sesi atau lakukan tindakan lain sesuai kebutuhan Anda
                        saveSesi(petugas!!.data?.username.toString())

                        // Setelah berhasil login, jika Anda ingin langsung ke MainActivity, Anda dapat menggunakan Intent seperti berikut:
                        val intentMain = Intent(this@Login, MainActivity::class.java)
                        startActivity(intentMain)
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,"Login Gagal",Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("errot", t.message.toString())
                }

            })
        }
        tmbsignup.setOnClickListener {
            Toast.makeText(applicationContext,"Nanti dibuatkan dulu registernya",Toast.LENGTH_SHORT).show()
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