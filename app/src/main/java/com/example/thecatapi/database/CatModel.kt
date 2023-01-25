package com.example.thecatapi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the model, that would be used for database.
 * Actually it is used for net either.
 */
@Entity(tableName = "cats")
class CatModel {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = ""

    @ColumnInfo(name = "image_link")
    var url: String? = null

}