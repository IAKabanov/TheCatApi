package com.example.thecatapi.iu

interface StarContract {
    /**
     * Suppose to be called when the "star" button is clicked.
     * It will give these parameters to the database. So if you
     * want to insert it to the database, catId is necessary.
     * If you want just find if there is a cat with such catUrl
     * pass the catId param.
     * @param catId is an id of the cat image that was clicked.
     * Might be skipped.
     * @param catUrl is an url to the image of the cat, that was
     * clicked.
     */
    fun star(catId: String = "", catUrl: String)
}