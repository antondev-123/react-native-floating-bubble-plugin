package com.floatingbubbleplugin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.floatingbubbleplugin.FloatingBubblePluginModule

class BootReceiver(private val module: FloatingBubblePluginModule) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
        //     // try {
        //     //     // val module = FloatingBubblePluginModule(context as ReactApplicationContext)
        //     //     module.restoreBubble()
        //     //     Log.d("BootReceiver", "Bubble restored!")
        //     // } catch (e:Exception) {
        //     //     Log.e("BootReceiver", "Failed to restore bubble")
        //     // }
        //      if (module.shouldRestoreOnBoot()) {
        //         module.restoreBubble()
        //     }
        // }

        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return
        try {
            if (module.shouldRestoreOnBoot()) {
                module.restoreBubble()
            }
        } catch (e: Exception) {
            Log.e("BootReceiver", "Restoration failed", e)
        }
    }
}