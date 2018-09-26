package com.example.junemon.travelroutine.network

import com.example.junemon.travelroutine.network.model.PersonalNews
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(ApiConfig.getTopHeadline)
    fun getTopHeadlineNewsBBC(@Query("sources") source: String?,
                              @Query("apiKey") keys: String?): Single<PersonalNews>

    @GET(ApiConfig.getTopHeadline)
    fun getTopHeadlineBussines(@Query("country") countries: String?,
                               @Query("category") categories: String?,
                               @Query("apiKey") keys: String?): Single<PersonalNewsBussines>

    @GET(ApiConfig.getTopHeadline)
    fun getTopHeadlineEntertainment(@Query("country") countries: String?,
                                    @Query("category") categories: String?,
                                    @Query("apiKey") keys: String?): Single<PersonalNewsEntertainment>
}