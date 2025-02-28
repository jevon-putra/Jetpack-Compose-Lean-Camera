package com.jop.jetpack.camera.view.camera.viewModel

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.data.repo.ImageRepository
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: ImageRepository): ViewModel() {
    val imageResult: MutableState<Bitmap?> = mutableStateOf(null)

    fun insert() = viewModelScope.launch {
        repository.insert(ImageCapture(imageBitmap = imageResult.value!!))
    }
}