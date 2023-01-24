package com.example.thecatapi.usecase

import com.example.thecatapi.domain.CatModel
import com.example.thecatapi.domain.Repository

class DBUsecase(private val repository: Repository) : StarUsecase, LoadingUsecase {
    override fun starUnstar(id: String, url: String): Boolean {
        val listCat = repository.findCat(url)
        return if (listCat.isNotEmpty()) {
            repository.delete(catUrl = url)
            false
        } else {
            repository.insert(id, url)
            true
        }
    }

    override fun loadAllTheCats(): List<CatModel> {
        return repository.getAllTheCats()
    }
}