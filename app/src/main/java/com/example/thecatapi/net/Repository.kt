package com.example.thecatapi.net

import com.example.thecatapi.database.CatModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Repository to access the "https://api.thecatapi.com/v1/images/".
 */
class Repository {
    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val api: CatRest = retrofit.create(CatRest::class.java)

    /**
     * This method gives ten cat links. This is a restriction of API.
     * No more than ten at once.
     * @return a list with at most ten links of cats
     */
    fun getAllTheCats(): List<CatModel> {
        val call: Call<List<CatModel>> = api.getImages(10)
        return call.execute().body() ?: ArrayList()
    }

}