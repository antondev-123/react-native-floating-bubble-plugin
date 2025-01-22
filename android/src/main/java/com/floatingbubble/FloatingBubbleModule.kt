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
    private var isInitialized: Boolean = false // Flag to track initialization
    private var pendingActions: MutableList<() -> Unit> = mutableListOf()

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
     if (!isInitialized) {
            // If not initialized, queue the action
            pendingActions.add {
                addNewBubble(x, y)
            }
            promise.resolve("Initialization pending")
            return
        }

        // If initialized, proceed with adding bubble
        try {
            this.addNewBubble(x, y)
        } catch (e: Exception) {
            promise.reject("Error adding bubble", e)
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

    @ReactMethod
    fun isBubbleVisible(promise: Promise) {
        try {
            val isVisible = bubbleView != null
            promise.resolve(isVisible)
        } catch (e: Exception) {
            promise.reject("Error checking bubble visibility", e)
        }
    }

    private fun addNewBubble(x: Int, y: Int) {
        this.removeBubble()
         try {
            bubbleView = LayoutInflater.from(reactContext).inflate(R.layout.bubble_layout, null) as BubbleLayout
            
            // Set up listeners and behaviors for the new bubble
            bubbleView!!.setOnBubbleRemoveListener {
                bubbleView = null
                sendEvent("floating-bubble-remove") // Send event when bubble is removed
            }
            bubbleView!!.setOnBubbleClickListener {
                sendEvent("floating-bubble-press") // Send event when bubble is clicked
            }
            bubbleView!!.setShouldStickToWall(true) // Ensure bubble sticks to the wall

            // Add the new bubble to the BubblesManager at the specified position
            bubblesManager!!.addBubble(bubbleView, x, y)
        } catch (e: Exception) {
            // Catch any errors during bubble creation and reject the promise
              Log.d("permison", "eject")
        }
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
                    isInitialized = true;
                }.build()
            bubblesManager!!.initialize()

    }

    private fun sendEvent(eventName: String) {
        val params = Arguments.createMap()
        reactContext.getJSModule<BridgeReactContext.RCTDeviceEventEmitter>(BridgeReactContext.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}