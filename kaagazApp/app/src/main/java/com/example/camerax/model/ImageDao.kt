package com.example.camerax.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface ImageDao {

    @Query("SELECT * FROM images Where PostId = :a")
    fun getImages(a:Int): LiveData<List<CameraEntity>>



}