package com.example.websmk

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var web: WebView
    private var ketukduakali = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = getSesiUsername()
        Toast.makeText(applicationContext,"Selamat Datang $username!",Toast.LENGTH_SHORT).show()

        web = findViewById(R.id.web_smk)
        web.webViewClient = WebViewClient()

        val webSetting = web.settings
        webSetting.javaScriptEnabled = true

        web.loadUrl("https://elearningsmkn2terbanggibesar.sch.id/")
    }

    private fun getSesiUsername():String{
        val sharedPref : SharedPreferences = getSharedPreferences(Login().PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("usnm", "") ?: ""
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