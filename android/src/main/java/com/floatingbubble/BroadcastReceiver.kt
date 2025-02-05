import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.floatingbubble.FloatingBubbleModule

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            try {
                val module = FloatingBubbleModule(context as ReactApplicationContext)
                module.restoreBubble()
                Log.d("BootReceiver", "Bubble restored!")
            } catch (e:Exception) {
                Log.e("BootReceiver", "Failed to restore bubble")
            }
        }
    }
}