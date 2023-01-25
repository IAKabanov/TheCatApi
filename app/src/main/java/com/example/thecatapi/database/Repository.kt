package com.example.thecatapi.database

import android.app.Application

/**
 * Repository to access the database.
 */
class Repository(application: Application) {
    private val database: CatDatabase = CatDatabase.getInstance(application)
    private val catDao = database.catDao()

    /**
     * Inserts CatModel into the database.
     * @param cat the CatModel to be inserted into the database.
     */
    fun insert(cat: CatModel) {
        val listCats = catDao.findId(cat)
        if (listCats.isEmpty()) {
            catDao.insert(cat)
        }
    }

    /**
     * Forms and then Inserts CatModel into the database.
     * @param catId the cat id.
     * @param catUrl the link to the cat image.
     */
    fun insert(catId: String, catUrl: String) {
        insert(createCatByIdAndUrl(catId, catUrl))
    }

    /**
     * Deletes CatModel from the database.
     * @param cat the CatModel to be deleted from the database.
     */
    fun delete(cat: CatModel) {
        val listCats = catDao.findId(cat)
        for (catToBeDeleted in listCats) {
            catDao.delete(catToBeDeleted)
        }
    }

    /**
     * Forms and then Deletes CatModel from the database.
     * @param catId the cat id.
     * @param catUrl the link to the cat image.
     */
    fun delete(catId: String? = null, catUrl: String) {
        delete(createCatByIdAndUrl(catId, catUrl))
    }

    /**
     * Provides all the cats contained in the database.
     * @return all the cats from the database.
     */
    fun getAllTheCats(): List<CatModel> {
        return catDao.getAllCats()
    }

    /**
     * Provides the list containing cats in the database
     * with specified url.
     * @param url the link to the cat image.
     * @return list of the cats with specified url.
     */
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