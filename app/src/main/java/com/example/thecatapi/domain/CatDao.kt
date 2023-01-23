package com.example.thecatapi.domain

import androidx.room.*


@Dao
interface CatDao {
    @Insert
    fun insert(cat: CatModel)

    @Query("SELECT * FROM cats")
    fun getAllCats(): List<CatModel>

    @Delete
    fun delete(cat: CatModel)

    @Query("SELECT * FROM cats where image_link = :url")
    fun findId(url: String): List<CatModel>

    @Transaction
    fun findId(cat: CatModel): List<CatModel> {
        val url = cat.url
        return if (url == null) {
            emptyList()
        } else {
            findId(url)
        }
    }
}