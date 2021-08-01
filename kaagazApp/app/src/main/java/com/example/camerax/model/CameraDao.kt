package com.example.camerax.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CameraDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setDBPosts(cameraEntity: CameraEntity)

    @Query("SELECT * FROM posts")
    fun getDBPosts(): LiveData<List<CameraEntity>>

    @Query("DELETE FROM posts")
    fun deleteAllPostsDB()


}