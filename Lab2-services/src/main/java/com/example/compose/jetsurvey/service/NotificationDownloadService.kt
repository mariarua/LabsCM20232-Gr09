package com.example.compose.jetsurvey.service;

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.compose.jetsurvey.MainActivity
import com.example.compose.jetsurvey.R

class NotificationDownloadService : Service() {


    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(1, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        Log.d("NotificationService", "buildNotification")
        return NotificationCompat.Builder(this, "channel_id")
            .setContentTitle("Descarga")
            .setContentText("Archivo descargado con éxito.")
            .setSmallIcon(R.drawable.ic_logo_light)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .build()
    }
}