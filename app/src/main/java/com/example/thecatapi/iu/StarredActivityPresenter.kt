package com.example.thecatapi.iu

import com.example.thecatapi.domain.CatModel
import com.example.thecatapi.usecase.LoadingUsecase
import com.example.thecatapi.usecase.StarUsecase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StarredActivityPresenter(private val activity: StarredActivity) {
    private var starUsecase: StarUsecase? = null
    private var getAllCatsUsecase: LoadingUsecase? = null

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

    fun onAllTheCatsLoaded(loaded: List<CatModel>) {
        activity.loaded(loaded)
    }

    fun setLoadingUsecase(usecase: LoadingUsecase) {
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
}