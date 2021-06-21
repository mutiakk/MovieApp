package com.example.movieappbisa.entity.local.source

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="movieDB")
data class MovieData(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "movieId")
    var id: Int,

    @ColumnInfo(name = "title")
    @NonNull
    var title: String,
    @ColumnInfo(name = "release")
    @NonNull
    var release: String,
    @ColumnInfo(name = "descr")
    @NonNull
    var descr: String,
    @ColumnInfo(name = "rate")
    @NonNull
    var rate: Double,
    @ColumnInfo(name = "poster")
    @NonNull
    var poster: String,
    @NonNull
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)