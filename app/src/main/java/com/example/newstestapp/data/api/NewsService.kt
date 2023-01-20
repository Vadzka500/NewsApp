package com.example.newstestapp.data.api

import com.example.newstestapp.utils.Constants.Companion.API_KEY_AUTHORIZATION
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeaders(
        @Query("q") query : String = "",
        @Query("country") country : String = "ru",
        @Query("page") page : Int = 1,
        @Header("Authorization") auth : String = API_KEY_AUTHORIZATION
    )
}