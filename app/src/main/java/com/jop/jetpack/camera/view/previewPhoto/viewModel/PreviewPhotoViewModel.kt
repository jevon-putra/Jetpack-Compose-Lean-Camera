package com.jop.jetpack.camera.view.previewPhoto.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.data.repo.ImageRepository
import kotlinx.coroutines.launch

class PreviewPhotoViewModel(private val repository: ImageRepository): ViewModel() {
    fun deleteImage(imageCapture: ImageCapture) = viewModelScope.launch {
        repository.delete(imageCapture)
    }
}