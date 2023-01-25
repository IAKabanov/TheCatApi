package com.example.thecatapi.iu

import android.content.ContentResolver
import android.graphics.Bitmap
import com.example.thecatapi.database.CatModel
import com.example.thecatapi.usecase.NetDownloader
import com.example.thecatapi.usecase.Downloader
import com.example.thecatapi.usecase.Starrer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

class MainActivityPresenter(private val activity: MainActivity) {
    private var starrer: Starrer? = null
    private var downloader: Downloader? = null
    private var netDownloader: NetDownloader? = null

    private fun onStarred(starred: Boolean, who: String) {
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

    private fun onDownloaded(where: String) {
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

    fun setNetDownloadUsecase(usecase: NetDownloader) {
        netDownloader = usecase
    }

    private suspend fun onNetDownloaded(downloaded: List<CatModel>, callback: KSuspendFunction0<Unit>) {
        activity.loadingProcess(downloaded, callback)
    }

    fun getCatsFromTheNet(callback: KSuspendFunction0<Unit>) {
        GlobalScope.launch {
            if (netDownloader != null) {
                val downloaded = netDownloader!!.getAllTheCats()
                onNetDownloaded(downloaded, callback)
            }
        }
    }
}