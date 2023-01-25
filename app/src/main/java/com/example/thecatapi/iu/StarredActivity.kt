package com.example.thecatapi.iu

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.*
import com.example.thecatapi.database.CatModel
import com.example.thecatapi.usecase.Downloader
import com.example.thecatapi.usecase.Loader
import com.example.thecatapi.usecase.Starrer


class StarredActivity : AppCompatActivity(), StarContract, DownloadContract {
    private lateinit var recyclerView: RecyclerView
    private lateinit var catAdapter: CatAdapter
    private lateinit var tvEmpty: TextView
    private lateinit var goToAll: Button
    private val presenter = StarredActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starred)

        recyclerView = findViewById(R.id.recyclerViewStarred)
        tvEmpty = findViewById(R.id.tvEmptyList)
        goToAll = findViewById(R.id.btn_all)
        goToAll.setOnClickListener{
            val intent = Intent(this@StarredActivity, MainActivity::class.java)
            startActivity(intent)
        }
        recyclerView.setHasFixedSize(true)

        catAdapter = CatAdapter(this)
        val layoutManager = GridLayoutManagerWrapper(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = catAdapter

        presenter.getAllTheCats()
    }

    private fun loadStarred(allCats: List<CatModel>) {
        catAdapter.addAll(allCats)
    }

    fun loaded(allCats: List<CatModel>) {
        loadStarred(allCats)
    }

    fun downloaded(where: String) {
        Toast.makeText(this, "Saved to $where", Toast.LENGTH_SHORT).show()
    }

    override fun download(iv: ImageView, cat: CatModel) {
        presenter.download(iv.drawable.toBitmap(), cat.id, contentResolver)
    }

    fun setLoadingUsecase(usecase: Loader) {
        presenter.setLoadingUsecase(usecase)
    }

    fun setStarUsecase(usecase: Starrer) {
        presenter.setStarUsecase(usecase)
    }

    fun setDownloadUsecase(usecase: Downloader) {
        presenter.setDownloadUsecase(usecase)
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