package com.example.thecatapi.domain

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatRest {
    @GET("search")
    fun getImages(@Query("limit") limit: Int): Call<List<CatModel>>
}