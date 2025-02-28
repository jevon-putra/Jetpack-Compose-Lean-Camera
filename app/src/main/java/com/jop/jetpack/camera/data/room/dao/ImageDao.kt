package com.jop.jetpack.camera.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jop.jetpack.camera.data.model.ImageCapture

@Dao
interface ImageDao {
    @Query("SELECT * FROM `image`")
    fun getAllImage(): LiveData<List<ImageCapture>>

    @Query("DELETE FROM `image`")
    suspend fun deleteALl()

    @Delete
    suspend fun delete(imageCapture: ImageCapture)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageCapture: ImageCapture)
}