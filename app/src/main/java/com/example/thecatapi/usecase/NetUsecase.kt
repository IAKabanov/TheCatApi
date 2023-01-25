package com.example.thecatapi.usecase

import com.example.thecatapi.database.CatModel
import com.example.thecatapi.net.Repository

class NetUsecase(val repository: Repository) : NetDownloader {
    override fun getAllTheCats(): List<CatModel> {
        return repository.getAllTheCats()
    }
}