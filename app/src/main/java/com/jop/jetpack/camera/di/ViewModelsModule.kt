package com.jop.jetpack.camera.di

import com.jop.jetpack.camera.view.camera.viewModel.CameraViewModel
import com.jop.jetpack.camera.view.home.viewModel.HomeViewModel
import com.jop.jetpack.camera.view.previewPhoto.viewModel.PreviewPhotoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CameraViewModel(get()) }
    viewModel { PreviewPhotoViewModel(get()) }
}