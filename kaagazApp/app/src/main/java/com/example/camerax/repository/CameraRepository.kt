package com.example.camerax.repository

import com.example.camerax.model.CameraDao
import com.example.camerax.model.CameraEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraRepository (val DAO:CameraDao){

    fun addUser(cameraEntity: CameraEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DAO.setDBPosts(cameraEntity)
        }
    }






}