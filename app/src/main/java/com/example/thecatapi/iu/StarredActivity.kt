package com.example.thecatapi.iu

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.*
import com.example.thecatapi.domain.CatModel
import com.example.thecatapi.usecase.LoadingUsecase
import com.example.thecatapi.usecase.StarUsecase
import java.io.File
import java.io.FileOutputStream


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

    fun setLoadingUsecase(usecase: LoadingUsecase) {
        presenter.setLoadingUsecase(usecase)
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