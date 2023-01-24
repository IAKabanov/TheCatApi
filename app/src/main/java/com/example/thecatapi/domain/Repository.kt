package com.example.thecatapi.domain

import android.app.Application

class Repository(application: Application) {
    private val database: CatDatabase = CatDatabase.getInstance(application)
    private val catDao = database.catDao()

    fun insert(cat: CatModel) {
        val listCats = catDao.findId(cat)
        if (listCats.isEmpty()) {
            catDao.insert(cat)
        }
    }

    fun insert(catId: String, catUrl: String) {
        insert(createCatByIdAndUrl(catId, catUrl))
    }


    fun delete(cat: CatModel) {
        val listCats = catDao.findId(cat)
        for (catToBeDeleted in listCats) {
            catDao.delete(catToBeDeleted)
        }
    }

    fun delete(catId: String? = null, catUrl: String) {
        delete(createCatByIdAndUrl(catId, catUrl))
    }

    fun getAllTheCats(): List<CatModel> {
        return catDao.getAllCats()
    }

    fun findCat(url: String): List<CatModel> {
        val cat = CatModel()
        cat.url = url
        return catDao.findId(cat)
    }

    private fun createCatByIdAndUrl(catId: String? = null, catUrl: String): CatModel {
        val cat = CatModel()
        val id = catId ?: ""
        cat.id = id
        cat.url = catUrl
        return cat
    }
}