package com.floatingbubbleplugin

import android.content.Context
import android.content.SharedPreferences

class BubbleStateManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("BubblePrefs", Context.MODE_PRIVATE)

    fun saveBubbleState(isVisible: Boolean, x: Int = 0, y: Int = 0) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isBubbleVisible", isVisible)
        editor.putInt("bubbleX", x)
        editor.putInt("bubbleY", y)
        editor.apply()
    }

    fun getBubbleState(): Triple<Boolean, Int, Int> {
        val isVisible = sharedPreferences.getBoolean("isBubbleVisible", false)
        val x = sharedPreferences.getInt("bubbleX", 100) // Default X position
        val y = sharedPreferences.getInt("bubbleY", 100) // Default Y position
        return Triple(isVisible, x, y)
    }
    fun shouldRestoreOnBoot(): Boolean {
        return sharedPreferences.getBoolean("restoreOnBoot", false)
    }

    fun setRestoreOnBoot(restore: Boolean) {
        sharedPreferences.edit().putBoolean("restoreOnBoot", restore).apply()
    }
}