package com.example.thecatapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.domain.CatModel
import com.squareup.picasso.Picasso


class CatAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val LOADING = 0
        const val ITEM = 1
    }

    private val catList = ArrayList<CatModel>()
    private var isLoadingAdded = false

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val download: ImageView = itemView.findViewById(R.id.download)
        val star: ImageView = itemView.findViewById(R.id.star)

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progress: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem = inflater.inflate(R.layout.cat_picture_item, parent, false)
                viewHolder = CatViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.cat_picture_loading, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cat = catList[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val catViewHolder: CatViewHolder = holder as CatViewHolder
                Picasso.get().load(cat.url).into(catViewHolder.image)
                catViewHolder.star.setOnClickListener {
                    if (context is StarContract) {
                        context.star(cat)
                    }
                }
                catViewHolder.download.setOnClickListener {
                    if (context is DownloadContract) {
                        context.download(catViewHolder.image, cat)
                    }
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progress.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position == catList.size - 1) && isLoadingAdded) LOADING else ITEM
    }

    fun add(cat: CatModel) {
        catList.add(cat)
        notifyItemInserted(catList.size - 1)
    }

    fun addAll(cats: List<CatModel>) {
        for (cat in cats) {
            add(cat)
        }
    }

    fun getItem(position: Int) : CatModel {
        return catList[position]
    }
}