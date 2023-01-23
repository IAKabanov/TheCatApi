package com.example.thecatapi

import com.example.thecatapi.domain.CatModel

interface StarContract {
    fun star(cat: CatModel)
    fun starred(starred: Boolean, cat: CatModel)
}