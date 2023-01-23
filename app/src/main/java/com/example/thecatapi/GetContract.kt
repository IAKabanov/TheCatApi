package com.example.thecatapi

import com.example.thecatapi.domain.CatModel

interface GetContract {
    fun onReceived(allCats: List<CatModel>)
}