package com.movies.tmdb_demo2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKeys")
data  class MovieRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val prev:Int?,
    val next:Int?
)