package com.jop.jetpack.camera.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.data.room.conventer.BitmapConverter
import com.jop.jetpack.camera.data.room.dao.ImageDao

@Database(entities = [ImageCapture::class], version = 1, exportSchema = false)
@TypeConverters(BitmapConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}