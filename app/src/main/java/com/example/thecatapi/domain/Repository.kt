package com.example.thecatapi.domain

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.example.thecatapi.GetContract
import com.example.thecatapi.StarContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class Repository(application: Application) {
    private val database: CatDatabase = CatDatabase.getInstance(application)
    val catDao = database.catDao()

    fun insert(cat: CatModel) {
        GlobalScope.launch {
            val listCats = catDao.findId(cat)
            if (listCats.isNotEmpty()) {
                catDao.insert(cat)
            }
        }
    }

    fun delete(cat: CatModel) {
        GlobalScope.launch {
            val listCats = catDao.findId(cat)
            for (catToBeDeleted in listCats) {
                catDao.delete(catToBeDeleted)
            }
        }
    }

    fun getAllTheCats(contract: GetContract) {
        GlobalScope.launch {
            contract.onReceived(catDao.getAllCats())
        }
    }

    fun star(cat: CatModel, contract: StarContract) {
        GlobalScope.launch {
            val listCats = catDao.findId(cat)
            if (listCats.isNotEmpty()) {
                catDao.delete(cat)
                contract.starred(false, cat)
            } else {
                catDao.insert(cat)
                contract.starred(true, cat)
            }
        }
    }
}