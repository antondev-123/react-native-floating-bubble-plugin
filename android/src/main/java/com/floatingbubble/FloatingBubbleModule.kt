package com.floatingbubble

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.BridgeReactContext
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.txusballesteros.bubbles.BubbleLayout
import com.txusballesteros.bubbles.BubblesManager

//package com.reactlibrary;

class FloatingBubbleModule
    (private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private var bubblesManager: BubblesManager? = null
    private var bubbleView: BubbleLayout? = null

    @ReactMethod
    fun reopenApp() {
        val launchIntent = reactContext.packageManager.getLaunchIntentForPackage(
            reactContext.packageName
        )
        if (launchIntent != null) {
            reactContext.startActivity(launchIntent)
        }
    }

    override fun getName(): String {
        return "FloatingBubble"
    }



    @ReactMethod // Notates a method that should be exposed to React
    fun showFloatingBubble(x: Int, y: Int, promise: Promise) {
        Log.d("Test", "my Message")
        try {
            this.addNewBubble(x, y)
            promise.resolve("")
        } catch (e: Exception) {
            promise.reject("")
        }
    }

    @ReactMethod // Notates a method that should be exposed to React
    fun hideFloatingBubble(promise: Promise) {
        try {
            this.removeBubble()
            promise.resolve("")
        } catch (e: Exception) {
            promise.reject("")
        }
    }

    @ReactMethod // Notates a method that should be exposed to React
    fun requestPermission(promise: Promise) {
        try {
            this.requestPermissionAction(promise)
        } catch (e: Exception) {
        }
    }

    @ReactMethod // Notates a method that should be exposed to React
    fun checkPermission(promise: Promise) {
        try {
            promise.resolve(hasPermission())
        } catch (e: Exception) {
            promise.reject("")
        }
    }

    @ReactMethod // Notates a method that should be exposed to React
    fun initialize(promise: Promise) {
        try {
            this.initializeBubblesManager()
            promise.resolve("")
        } catch (e: Exception) {
            promise.reject("")
        }
    }

    private fun addNewBubble(x: Int, y: Int) {
        this.removeBubble()
        bubbleView =
            LayoutInflater.from(reactContext).inflate(R.layout.bubble_layout, null) as BubbleLayout
        bubbleView!!.setOnBubbleRemoveListener {
            bubbleView = null
            sendEvent("floating-bubble-remove")
        }
        bubbleView!!.setOnBubbleClickListener { sendEvent("floating-bubble-press") }
        bubbleView!!.setShouldStickToWall(true)
        Log.d("bubblesManager", "aaa");
        bubblesManager!!.addBubble(bubbleView, x, y)
    }

    private fun hasPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(reactContext)
        }
        return true
    }

    private fun removeBubble() {
        if (bubbleView != null) {
            try {
                bubblesManager!!.removeBubble(bubbleView)
            } catch (e: Exception) {
            }
        }
    }


    fun requestPermissionAction(promise: Promise) {
        Log.d("permison", "start")
        if (!hasPermission()) {
            Log.d("permison", "No")
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + reactContext.packageName)
            )
            val bundle = Bundle()
            reactContext.startActivityForResult(intent, 0, bundle)
        }
        if (hasPermission()) {
            Log.d("permison", "Yes")
            promise.resolve("")
        } else {
            promise.reject("")
        }
    }

    private fun initializeBubblesManager() {
        bubblesManager =
            BubblesManager.Builder(reactContext).setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback {
                    // addNewBubble();
                }.build()
            bubblesManager!!.initialize()

    }

    private fun sendEvent(eventName: String) {
        val params = Arguments.createMap()
        reactContext.getJSModule<BridgeReactContext.RCTDeviceEventEmitter>(BridgeReactContext.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}