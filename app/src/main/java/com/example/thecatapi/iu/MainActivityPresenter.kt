package com.example.thecatapi.iu

import com.example.thecatapi.usecase.StarUsecase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityPresenter(private val activity: MainActivity) {
    private var starUsecase: StarUsecase? = null

    fun onStarred(starred: Boolean, who: String) {
        activity.starred(starred, who)
    }

    fun setStarUsecase(usecase: StarUsecase) {
        starUsecase = usecase
    }

    fun starUnstar(catId: String, catUrl: String) {
        GlobalScope.launch {
            if (starUsecase != null) {
                val starred = starUsecase!!.starUnstar(catId, catUrl)
                onStarred(starred, catId)
            }
        }
    }

}