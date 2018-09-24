package com.example.junemon.travelroutine.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        val gson: Gson = GsonBuilder().setLenient().create()

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(ApiConfig.newsBaseUrl)
                    .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}