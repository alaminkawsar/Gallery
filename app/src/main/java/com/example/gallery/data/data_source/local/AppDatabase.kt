package com.example.gallery.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gallery.data.model.PhotoModel

@Database(entities = [PhotoModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}