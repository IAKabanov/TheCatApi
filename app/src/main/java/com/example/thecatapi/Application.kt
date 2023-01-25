package com.example.thecatapi

import android.app.Activity
import android.os.Bundle
import com.example.thecatapi.database.Repository
import com.example.thecatapi.iu.MainActivity
import com.example.thecatapi.iu.StarredActivity
import com.example.thecatapi.usecase.DBUsecase
import com.example.thecatapi.usecase.DownloadUsecase
import com.example.thecatapi.usecase.NetUsecase

class Application : android.app.Application(), android.app.Application.ActivityLifecycleCallbacks {
    private lateinit var dbRepo: Repository
    private lateinit var dbUsecase: DBUsecase
    private lateinit var downloadUsecase: DownloadUsecase
    private lateinit var netRepo: com.example.thecatapi.net.Repository
    private lateinit var netUsecase: NetUsecase
    override fun onCreate() {
        super.onCreate()
        dbRepo = Repository(this)
        dbUsecase = DBUsecase(dbRepo)
        downloadUsecase = DownloadUsecase()
        netRepo = com.example.thecatapi.net.Repository()
        netUsecase = NetUsecase(netRepo)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is MainActivity) {
            activity.setStarUsecase(dbUsecase)
            activity.setDownloadUsecase(downloadUsecase)
            activity.setNetDownloadUsecase(netUsecase)
        } else if (activity is StarredActivity) {
            activity.setStarUsecase(dbUsecase)
            activity.setLoadingUsecase(dbUsecase)
            activity.setDownloadUsecase(downloadUsecase)
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}