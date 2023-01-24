package com.example.thecatapi

import android.app.Activity
import android.os.Bundle
import com.example.thecatapi.domain.Repository
import com.example.thecatapi.iu.MainActivity
import com.example.thecatapi.iu.StarredActivity
import com.example.thecatapi.usecase.DBUsecase

class Application : android.app.Application(), android.app.Application.ActivityLifecycleCallbacks {
    private lateinit var repo: Repository
    private lateinit var usecase: DBUsecase
    override fun onCreate() {
        super.onCreate()
        repo = Repository(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        usecase = DBUsecase(repo)
        if (activity is MainActivity) {
            activity.setStarUsecase(usecase)
        } else if (activity is StarredActivity) {
            activity.setStarUsecase(usecase)
            activity.setLoadingUsecase(usecase)
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