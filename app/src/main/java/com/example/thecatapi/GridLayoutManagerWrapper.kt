package com.example.thecatapi

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class GridLayoutManagerWrapper(context: Context, span: Int): GridLayoutManager(context, span) {
    override fun onLayoutChildren(recycler: Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("TAG", "There's a bug in RecyclerView since 23.1.1")
        }
    }
}