package com.floatingbubble

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.os.Build

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Reboot detected! Restarting Floating Bubble...")
            val serviceIntent = Intent(context, FloatingBubbleService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For API 26+ (Android 8.0+), use startForegroundService
                context.startForegroundService(serviceIntent)
            } else {
                // For older versions, use startService instead
                context.startService(serviceIntent)
            }
        }
    }
}
