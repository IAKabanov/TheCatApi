package com.example.thecatapi.usecase

import android.content.ContentResolver
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

class DownloadUsecase : Downloader {
    override fun download(
        bitmap: Bitmap,
        path: String,
        name: String,
        contentResolver: ContentResolver
    ): Boolean {
        try {
            val file = File(path, "$name .jpg")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
            MediaStore.Images.Media.insertImage(
                contentResolver,
                file.absolutePath,
                file.name,
                file.name
            )
            return true
        } catch (ex: Exception) {
            ex.message
            return false
        }
    }
}