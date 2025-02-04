package com.floatingbubble

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

class FloatingBubbleService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("FloatingBubbleService", "Floating Bubble Service Created")

        // Show bubble when service starts
        FloatingBubbleModule.instance?.showFloatingBubble(100, 200)

        // Create a persistent notification
        createNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY  // Ensures the service restarts after termination
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification() {
        val channelId = "floating_bubble_service"
        val channelName = "Floating Bubble Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = Notification.Builder(this, channelId)
            .setContentTitle("Floating Bubble Active")
            .setContentText("Tap to open app")
            .setSmallIcon(R.drawable.ic_bubble)
            .build()
            startForeground(1, notification)
        } else {
            val notification = Notification.Builder(this)
            .setContentTitle("Floating Bubble Active")
            .setContentText("Tap to open app")
            .setSmallIcon(R.drawable.ic_bubble)
            .build()
            startForeground(1, notification)
        }

        

    }
}
