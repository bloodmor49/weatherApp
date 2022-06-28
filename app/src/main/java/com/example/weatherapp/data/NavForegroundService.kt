package com.example.weatherapp.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R
import com.example.weatherapp.other.Constants.CHANNEL_ID
import com.example.weatherapp.other.Constants.CHANNEL_NAME
import com.example.weatherapp.other.Constants.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NavForegroundService() : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private fun createNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(false)
            .setContentTitle("WeatherApp")
            .setContentText("Определяем местоположение")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
    }

    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val notificationBuilder = createNotificationBuilder()
        createNotificationChannel(notificationManager)
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }


    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        coroutineScope.launch {
            intent?.let {
                Log.d("Service", "Started")
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}

