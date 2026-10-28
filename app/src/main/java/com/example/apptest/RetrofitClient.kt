package com.example.apptest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 这里写 127.0.0.1，因为我们刚才做了 adb 端口反向代理
object RetrofitClient {
    private const val BASE_URL = "http://127.0.0.1:8080/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
