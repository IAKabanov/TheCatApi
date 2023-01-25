package com.example.thecatapi.usecase

import com.example.thecatapi.database.CatModel

interface NetDownloader {
    /**
     * That method suppose to get all the cats from "https://api.thecatapi.com/v1/images/".
     * @return a list of the all the cats, got from the url.
     */
    fun getAllTheCats(): List<CatModel>
}