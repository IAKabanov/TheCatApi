package com.example.thecatapi.iu

import com.example.thecatapi.domain.CatModel

interface StarContract {
    fun star(id: String = "", catUrl: String)
}