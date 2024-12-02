package com.example.gallery.di

import android.content.Context
import androidx.room.Room
import com.example.gallery.data.data_source.local.AppDatabase
import com.example.gallery.data.repository.FetchRemoteAlbum
import com.example.gallery.data.repository.FetchRemotePhoto
import com.example.gallery.data.repository.FetchRemoteUserData
import com.example.gallery.data.repository.LocalDatabaseRepositoryImpl
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.domain.usecase.DatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRemoteDatabaseRepository(): RemoteDatabaseRepository {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(5, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(5, TimeUnit.SECONDS)   // Write timeout
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(RemoteDatabaseRepository::class.java)
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "PhotoModel"
        ).build()
    }
    @Provides
    @Singleton
    fun provideDatabaseUseCase(
        remoteDbRepository: RemoteDatabaseRepository,
        appDatabase: AppDatabase
    ): DatabaseUseCase {
        return DatabaseUseCase(
            fetchRemoteAlbum = FetchRemoteAlbum(remoteDbRepo = remoteDbRepository),
            fetchRemotePhoto = FetchRemotePhoto(remoteDbRepo = remoteDbRepository),
            fetchRemoteUserData = FetchRemoteUserData(remoteDbRepo = remoteDbRepository),
            localdb = LocalDatabaseRepositoryImpl(appDatabase.photoDao())
        )
    }
}