package com.example.thecatapi.iu

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.*
import com.example.thecatapi.database.CatModel
import com.example.thecatapi.usecase.NetDownloader
import com.example.thecatapi.usecase.Downloader
import com.example.thecatapi.usecase.Starrer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction0


class MainActivity : AppCompatActivity(), StarContract, DownloadContract {
    companion object {
        const val PAGE_START = 1
        const val TOTAL_PAGES = Int.MAX_VALUE
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var catAdapterPagination: CatAdapterPagination
    private lateinit var goToStarred: Button
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = PAGE_START
    private lateinit var progressBar: ProgressBar
    private val presenter = MainActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        goToStarred = findViewById(R.id.btn_starred)
        goToStarred.setOnClickListener {
            val intent = Intent(this@MainActivity, StarredActivity::class.java)
            startActivity(intent)
        }
        recyclerView.setHasFixedSize(true)

        catAdapterPagination = CatAdapterPagination(this)
        val layoutManager = GridLayoutManagerWrapper(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = catAdapterPagination
        recyclerView.addOnScrollListener(object : CatScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        loadFirstPage()
    }

    fun setNetDownloadUsecase(usecase: NetDownloader) {
        presenter.setNetDownloadUsecase(usecase)
    }

    suspend fun firstPageLoaded() {
        withContext(Dispatchers.Main) {
            progressBar.visibility = View.GONE
        }
    }

    suspend fun nextPageLoaded() {
        withContext(Dispatchers.Main) {
            catAdapterPagination.removeLoadingFooter()
            isLoading = false
        }
    }

    suspend fun loadingProcess(cats: List<CatModel>, callback: KSuspendFunction0<Unit>) {
        callback()
        catAdapterPagination.addAll(cats)
        if (currentPage <= TOTAL_PAGES) catAdapterPagination.addLoadingFooter()
        else isLastPage = true
    }

    private fun loadFirstPage() {
        presenter.getCatsFromTheNet(::firstPageLoaded)
    }

    private fun loadNextPage() {
        presenter.getCatsFromTheNet(::nextPageLoaded)
    }

    fun setDownloadUsecase(usecase: Downloader) {
        presenter.setDownloadUsecase(usecase)
    }

    fun downloaded(where: String) {
        Toast.makeText(this, "Saved to $where", Toast.LENGTH_SHORT).show()
    }

    override fun download(iv: ImageView, cat: CatModel) {
        presenter.download(iv.drawable.toBitmap(), cat.id, contentResolver)
    }

    fun setStarUsecase(usecase: Starrer) {
        presenter.setStarUsecase(usecase)
    }

    override fun star(catId: String, catUrl: String) {
        presenter.starUnstar(catId, catUrl)
    }

    fun starred(starred: Boolean, cat: String) {
        val toBeShown = if (starred) "starred" else "unstarred"
        try {
            Toast.makeText(this, "$cat $toBeShown", Toast.LENGTH_SHORT).show()
        } catch (ex: NullPointerException) {
            Looper.prepare()
            Toast.makeText(this, "$cat $toBeShown", Toast.LENGTH_SHORT).show()
        }
    }
}