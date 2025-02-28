package com.jop.jetpack.camera.di

import androidx.room.Room
import com.jop.jetpack.camera.data.room.LocalDatabase
import com.jop.jetpack.camera.data.room.dao.ImageDao
import org.koin.dsl.module

val databaseModule = module {
    single<LocalDatabase> {
        Room.databaseBuilder(get(), LocalDatabase::class.java, "learn_camera")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ImageDao> { get<LocalDatabase>().imageDao() }
}