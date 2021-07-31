package com.example.camerax.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Posts")
data class CameraEntity(
    @ColumnInfo(name = "image") var image : String,
    @ColumnInfo(name = "subTitle") var subTitle : String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null
)



