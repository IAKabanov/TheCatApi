package com.example.thecatapi.usecase

import android.content.ContentResolver
import android.graphics.Bitmap

interface Downloader {
    /**
     * Suppose to be invoked when it's time to download an image to the gallery.
     * @param bitmap is an image to be downloaded, got from an ImageView.
     * @param path is a path that image to be downloaded to.
     * @param name is a name that image to be downloaded with.
     * @param contentResolver is a service thing, that is used for downloading.
     * @return true if downloading successful, false otherwise.
     */
    fun download(
        bitmap: Bitmap,
        path: String,
        name: String,
        contentResolver: ContentResolver
    ): Boolean
}