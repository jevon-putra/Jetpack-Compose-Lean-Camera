package com.jop.jetpack.camera.di

import com.jop.jetpack.camera.data.repo.ImageRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single { ImageRepository(get()) }
}