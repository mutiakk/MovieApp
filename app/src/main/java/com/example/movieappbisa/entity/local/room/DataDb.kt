package com.example.movieappbisa.entity.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData

@Database(entities = [MovieData::class, TvShowData::class],
    version = 1,
    exportSchema = false)
abstract class DataDb: RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object{

        @Volatile
        private var INSTANCE: DataDb? = null

        fun getInstance(context: Context): DataDb =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    DataDb::class.java,
                    "movietv.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}