package com.example.thecatapi.usecase

interface Starrer {
    /**
     * That method suppose to add/delete an object in database.
     * @param id is an id of the cat.
     * @param url is a link to the cat image.
     * @return true if a cat is starred, false otherwise
     */
    fun starUnstar(id: String, url: String): Boolean
}