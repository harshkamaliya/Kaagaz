package com.example.camerax

import android.app.Application
import com.example.camerax.model.CameraDataBase
import com.example.camerax.repository.CameraGetRepository
import com.example.camerax.repository.CameraRepository

class CameraApplication:Application() {

    val userDAO by lazy {
        val roomDatabase = CameraDataBase.getDataBase(this)
        roomDatabase.getCameraDao()
    }


    val cameraRepository by lazy {
        CameraRepository(userDAO)
    }

    val cameraGetRepository by lazy {
        CameraGetRepository(userDAO)
    }


}