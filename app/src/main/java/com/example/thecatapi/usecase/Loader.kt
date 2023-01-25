package com.example.thecatapi.usecase

import com.example.thecatapi.database.CatModel

interface Loader {
    /**
     * That method suppose to get all the cats from the database.
     * @return a list of the all the cats, got from the database.
     */
    fun loadAllTheCats(): List<CatModel>
}