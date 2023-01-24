package com.example.thecatapi.iu

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.*
import com.example.thecatapi.domain.CatModel
import com.example.thecatapi.domain.CatRest
import com.example.thecatapi.usecase.StarUsecase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream


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

    private fun loadFirstPage() {
        val retrofit = Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: CatRest = retrofit.create(CatRest::class.java)
        val call: Call<List<CatModel>> = api.getImages(10)
        call.enqueue(object : Callback<List<CatModel>> {
            override fun onResponse(
                call: Call<List<CatModel>>,
                response: Response<List<CatModel>>
            ) {
                if (response.isSuccessful) {
                    val results = response.body()
                    if (results != null) {
                        progressBar.visibility = View.GONE
                        catAdapterPagination.addAll(results)
                    }
                    if (currentPage <= TOTAL_PAGES) catAdapterPagination.addLoadingFooter()
                    else isLastPage = true
                }
            }

            override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
                Log.d("main", "onFailure: " + t.message)
            }
        })
    }

    private fun loadNextPage() {
        val retrofit = Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: CatRest = retrofit.create(CatRest::class.java)
        val call: Call<List<CatModel>> = api.getImages(10)

        call.enqueue(object : Callback<List<CatModel>> {
            override fun onResponse(
                call: Call<List<CatModel>>,
                response: Response<List<CatModel>>
            ) {
                catAdapterPagination.removeLoadingFooter()
                isLoading = false
                val results = response.body()
                if (results != null) {
                    catAdapterPagination.addAll(results)
                }
                if (currentPage <= TOTAL_PAGES) catAdapterPagination.addLoadingFooter()
                else isLastPage = true
            }

            override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun download(iv: ImageView, cat: CatModel) {
        try {
            val file = File("/sdcard/Download", cat.id + ".jpg")
            val fOut = FileOutputStream(file)
            val bitmap: Bitmap = iv.drawable.toBitmap()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
            MediaStore.Images.Media.insertImage(
                contentResolver,
                file.absolutePath,
                file.name,
                file.name
            )
            Toast.makeText(this, "Saved to ${file.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun setStarUsecase(usecase: StarUsecase) {
        presenter.setStarUsecase(usecase)
    }

    override fun star(id: String, catUrl: String) {
        presenter.starUnstar(id, catUrl)
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