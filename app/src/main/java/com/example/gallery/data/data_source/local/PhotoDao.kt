package com.example.gallery.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gallery.data.model.PhotoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Query("SELECT * FROM PhotoModel ORDER BY uid DESC LIMIT 10")
    fun getPhotos(): Flow<List<PhotoModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoModel>)

}