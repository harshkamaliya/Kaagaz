package com.example.camerax

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.camerax.repository.CameraGetRepository
import com.example.camerax.repository.CameraRepository
import com.example.camerax.viewmodel.CameraGetViewModel
import com.example.camerax.viewmodel.CameraViewModel

class CameraViewModelFactory(val repository: CameraRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CameraViewModel(repository) as T
    }

}
class CameraGetViewModelFactory(val repository: CameraGetRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CameraGetViewModel(repository) as T
    }

}