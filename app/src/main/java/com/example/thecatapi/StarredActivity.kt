package com.example.thecatapi

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.domain.CatModel
import com.example.thecatapi.domain.Repository
import java.io.File
import java.io.FileOutputStream


class StarredActivity : AppCompatActivity(), StarContract, DownloadContract, GetContract {
    private lateinit var recyclerView: RecyclerView
    private lateinit var catAdapter: CatAdapter
    private lateinit var tvEmpty: TextView
    private lateinit var goToAll: Button
    private lateinit var repo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starred)

        repo = Repository(application)

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

        repo.getAllTheCats(this)
    }

    private fun loadStarred(allCats: List<CatModel>) {
        catAdapter.addAll(allCats)
    }

    override fun star(cat: CatModel) {
        if (cat.url == null) return
        repo.star(cat, this)
    }

    override fun starred(starred: Boolean, cat: CatModel) {
        val toBeShown = if (starred) "starred" else "unstarred"
        Toast.makeText(this, "${cat.id} $toBeShown", Toast.LENGTH_SHORT).show()
    }

    override fun onReceived(allCats: List<CatModel>) {
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

}