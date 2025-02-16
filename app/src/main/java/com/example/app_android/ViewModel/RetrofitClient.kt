package com.example.app_android.ViewModel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val BASE_URL = "http://10.0.2.2:3000//api/"
    val instance: ApiService by lazy {
        Retrofit.Builder()
            //establece la url
            .baseUrl(BASE_URL)
            //establece la forma de conversión de los datos
            .addConverterFactory(GsonConverterFactory.create())
            //construye
            .build()
            //devuelve una instancia con ApiService implementada
            .create(ApiService::class.java)
    }
}