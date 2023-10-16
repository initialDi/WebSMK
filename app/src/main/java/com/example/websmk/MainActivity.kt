package com.example.websmk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var web: WebView
    private var ketukduakali = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = getSesiUsername()
        Toast.makeText(applicationContext,"Selamat Datang $userId!",Toast.LENGTH_SHORT).show()

        web = findViewById(R.id.web_smk)
        val fabLogout: FloatingActionButton = findViewById(R.id.fab_logout)
        web.webViewClient = WebViewClient()

        val webSetting = web.settings
        webSetting.javaScriptEnabled = true

        // Mengirim data ke WebView menggunakan JavaScript
        web.loadUrl("javascript:setUserId('" + userId + "')")

        web.loadUrl("https://rinaldiihza.000webhostapp.com/includes/contoh.php?userId="+ userId)

        fabLogout.setOnClickListener {
            // Hapus sesi pengguna (logout)
            clearSession()

            // Arahkan pengguna ke layar login
            val intentLogin = Intent(this, Login::class.java)
            startActivity(intentLogin)
            finish() // Tutup aktivitas saat ini agar pengguna tidak dapat kembali ke layar logout
        }

    }

    private fun getSesiUsername():String{
        val sharedPref : SharedPreferences = getSharedPreferences(Login().PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("usnm", "") ?: ""
    }

    private fun clearSession() {
        val sharedPref: SharedPreferences = getSharedPreferences(Login().PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("usnm") // Hapus data pengguna yang ingin dihapus
        editor.putBoolean("isLogedIn", false) // Set status login menjadi false
        editor.apply()
    }

    override fun onBackPressed() {
        if (web.canGoBack()){
            web.goBack()
        }
        else{
            if (ketukduakali) {
                super.onBackPressed()
                finish()
                return
            }
            this.ketukduakali = true
            Toast.makeText(this,"Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                ketukduakali = false
            }, 2000)
        }
    }
}