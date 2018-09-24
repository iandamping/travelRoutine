package com.example.junemon.travelroutine.network

import com.example.junemon.travelroutine.network.model.NewsAll
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(ApiConfig.getTopHeadline)
    fun getTopHeadlineNews(@Query("sources") source: String?,
                           @Query("apiKey") keys: String?): Observable<NewsAll>
}