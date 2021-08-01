package com.example.camerax.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.camerax.model.CameraEntity
import com.example.camerax.repository.CameraGetRepository

class CameraGetViewModel(val repository: CameraGetRepository):ViewModel() {

    fun getUserList(): LiveData<List<CameraEntity>> {
        return repository.getUserList()
    }
}