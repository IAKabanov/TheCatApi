package com.example.thecatapi.iu

import android.widget.ImageView
import com.example.thecatapi.database.CatModel

interface DownloadContract {
    /**
     * Suppose to be invoked when "download" icon is clicked.
     * @param iv is an ImageView, that related clicked
     * "download" button
     * @param cat is a CatModel, that supposed to be used
     * in the ImageView
     */
    fun download(iv: ImageView, cat: CatModel)
}