package com.example.thecatapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

/**
 * Here is Room lib database created.
 */
@Database(entities = [CatModel::class], version = 1)
abstract class CatDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: CatDatabase? = null
        private const val DATABASE_NAME = "CatDatabase"
        fun getInstance(context: Context): CatDatabase {
            if (INSTANCE == null) {
                synchronized(CatDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(context, CatDatabase::class.java, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }

    abstract fun catDao(): CatDao
}