package com.example.thecatapi.usecase

interface StarUsecase {
    /**
     * That method suppose to add/delete an object in database.
     * @param url a String, that represents an object.
     */
    fun starUnstar(id: String, url: String): Boolean
}