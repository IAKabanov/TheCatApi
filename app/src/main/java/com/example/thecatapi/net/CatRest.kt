package com.example.thecatapi.net

import com.example.thecatapi.database.CatModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface is necessary for Retrofit.
 */
interface CatRest {
    @GET("search")
    fun getImages(@Query("limit") limit: Int): Call<List<CatModel>>
}