package com.example.thecatapi.iu

import android.content.ContentResolver
import android.graphics.Bitmap
import com.example.thecatapi.database.CatModel
import com.example.thecatapi.usecase.Downloader
import com.example.thecatapi.usecase.Loader
import com.example.thecatapi.usecase.Starrer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StarredActivityPresenter(private val activity: StarredActivity) {
    private var starrer: Starrer? = null
    private var getAllCatsUsecase: Loader? = null
    private var downloader: Downloader? = null

    fun onStarred(starred: Boolean, who: String) {
        activity.starred(starred, who)
    }

    fun setStarUsecase(usecase: Starrer) {
        starrer = usecase
    }

    fun starUnstar(catId: String, catUrl: String) {
        GlobalScope.launch {
            if (starrer != null) {
                val starred = starrer!!.starUnstar(catId, catUrl)
                onStarred(starred, catId)
            }
        }
    }

    fun onAllTheCatsLoaded(loaded: List<CatModel>) {
        activity.loaded(loaded)
    }

    fun setLoadingUsecase(usecase: Loader) {
        getAllCatsUsecase = usecase
    }

    fun getAllTheCats() {
        GlobalScope.launch {
            if (getAllCatsUsecase != null) {
                val loaded = getAllCatsUsecase!!.loadAllTheCats()
                onAllTheCatsLoaded(loaded)
            }
        }
    }

    fun onDownloaded(where: String) {
        activity.downloaded(where)
    }

    fun setDownloadUsecase(usecase: Downloader) {
        downloader = usecase
    }

    fun download(bitmap: Bitmap, name: String, contentResolver: ContentResolver) {
        GlobalScope.launch {
            if (downloader != null) {
                val path = "/sdcard/Download"
                val downloaded = downloader!!.download(bitmap, path, name, contentResolver)
                if (downloaded) {
                    onDownloaded("$path/$name")
                }
            }
        }
    }
}