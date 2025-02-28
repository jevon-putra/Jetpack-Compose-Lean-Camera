package com.jop.jetpack.camera.data.repo

import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.data.room.dao.ImageDao

class ImageRepository(private val imageDao: ImageDao) {
    fun getAllImages() = imageDao.getAllImage()

    suspend fun delete(imageCapture: ImageCapture) = imageDao.delete(imageCapture)

    suspend fun insert(imageCapture: ImageCapture) = imageDao.insert(imageCapture)

    suspend fun deleteAll() = imageDao.deleteALl()
}