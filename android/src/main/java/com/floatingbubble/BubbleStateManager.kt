package com.floatingbubble

import android.content.Context
import android.content.SharedPreferences

class BubbleStateManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("BubblePrefs", Context.MODE_PRIVATE)

    fun saveBubbleState(isVisible: Boolean, x:Int, y:Int) {
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
}