package com.example.thecatapi

import android.widget.ImageView
import com.example.thecatapi.domain.CatModel

interface DownloadContract {
    fun download(iv: ImageView, cat: CatModel)
}