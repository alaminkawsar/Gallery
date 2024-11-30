package com.example.gallery.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.gallery.domain.usecase.PhotosUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DataSyncService: Service() {

    @Inject
    lateinit var photosUseCase: PhotosUseCase
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = photosUseCase.fetchAndSaveRemoteData().first().data?: "Can't fetch data"
                Log.d("DataSyncService", data)
                val broadcastIntent = Intent("com.example.gallery.DATA_SYNC")
                broadcastIntent.putExtra("data", data)
                sendBroadcast(broadcastIntent) // Broadcast the intent

            } catch (e: Exception) {
                e.printStackTrace()
//                Log.d("DataSyncService", "${e.message}")

            } finally {
//                Log.d("DataSyncService", "stop service")
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}