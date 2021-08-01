package com.example.camerax.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @ColumnInfo(name = "PostId") var postId : String,
    @ColumnInfo(name="imageURI") var imageURI:String,
)
{
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null
}