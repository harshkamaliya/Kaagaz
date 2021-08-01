package com.example.camerax.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Posts")
data class CameraEntity(
    @ColumnInfo(name = "image_name") var name : String,
    @ColumnInfo(name="time_stamp") var time:String,
    @ColumnInfo(name = "album_name") var album: String,
    @ColumnInfo(name="path") var path:String,)
{
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null
}





