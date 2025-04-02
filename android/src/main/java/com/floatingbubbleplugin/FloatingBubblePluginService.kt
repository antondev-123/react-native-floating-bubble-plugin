package com.floatingbubbleplugin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.facebook.react.bridge.ReactApplicationContext

class FloatingBubblePluginService : Service() {
    private var module: FloatingBubblePluginModule? = null
    
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        // Initialize your module here
        module = FloatingBubblePluginModule(applicationContext as ReactApplicationContext)
        module?.initialize()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        module?.restoreBubble()
        return START_STICKY
    }

    override fun onDestroy() {
        // module?.unregisterBootReceiver()
        module?.onCatalystInstanceDestroy()
        super.onDestroy()
    }
}