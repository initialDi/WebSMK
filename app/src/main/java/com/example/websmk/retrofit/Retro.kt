package com.example.websmk.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://rinaldiihza.000webhostapp.com/loginapp.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}