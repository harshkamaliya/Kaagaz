package com.example.camerax.viewmodel

import androidx.lifecycle.ViewModel
import com.example.camerax.model.CameraEntity
import com.example.camerax.repository.CameraRepository

class CameraViewModel(val repository: CameraRepository):ViewModel() {


    fun addUser(cameraEntity:CameraEntity)
    {
        repository.addUser(cameraEntity)

    }

}