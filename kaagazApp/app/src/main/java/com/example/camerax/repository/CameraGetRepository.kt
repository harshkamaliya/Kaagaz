package com.example.camerax.repository

import androidx.lifecycle.LiveData
import com.example.camerax.model.CameraDao
import com.example.camerax.model.CameraEntity

class CameraGetRepository(val DAO:CameraDao) {

    fun getUserList(): LiveData<List<CameraEntity>> {
        return DAO.getDBPosts()
    }


}