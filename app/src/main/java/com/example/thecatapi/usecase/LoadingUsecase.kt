package com.example.thecatapi.usecase

import com.example.thecatapi.domain.CatModel

interface LoadingUsecase {
    /**
     * That method suppose to get all the cats from the database.
     */
    fun loadAllTheCats(): List<CatModel>
}