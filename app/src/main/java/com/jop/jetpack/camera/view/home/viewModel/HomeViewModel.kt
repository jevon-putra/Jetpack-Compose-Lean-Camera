package com.jop.jetpack.camera.view.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.data.repo.ImageRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ImageRepository): ViewModel() {
    val getAllImages = repository.getAllImages()

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}