package com.techafresh.frogonotificationcompose.reply

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.frogobox.notification.FrogoNotification
import com.techafresh.frogonotificationcompose.reply.NotificationService.Companion.CHANNEL_ID
import com.techafresh.frogonotificationcompose.reply.NotificationService.Companion.CHANNEL_NAME
import com.techafresh.frogonotificationcompose.reply.NotificationService.Companion.REPLY_ACTION

class NotificationBroadcastReceiver : BroadcastReceiver(){
    companion object {
        private const val KEY_NOTIFICATION_ID = "key_notification_id"
        private const val KEY_MESSAGE_ID = "key_message_id"

        fun getReplyMessageIntent(context: Context, notificationId: Int, messageId: Int): Intent {
            val intent = Intent(context, NotificationBroadcastReceiver::class.java)
            intent.action = REPLY_ACTION
            intent.putExtra(KEY_NOTIFICATION_ID, notificationId)
            intent.putExtra(KEY_MESSAGE_ID, messageId)
            return intent
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (REPLY_ACTION == intent.action) {
            val message = NotificationService.getReplyMessage(intent)
            val messageId = intent.getIntExtra(KEY_MESSAGE_ID, 0)
            val notifyId = intent.getIntExtra(KEY_NOTIFICATION_ID, 1)
            Toast.makeText(context, "Message ID: $messageId\nMessage: $message", Toast.LENGTH_LONG).show()
            updateNotification(context, notifyId, "$message")
        }
    }

    private fun updateNotification(context: Context, notifyId: Int, message : String) {
        FrogoNotification.Inject(context)
            .setChannelId(CHANNEL_ID)
            .setChannelName(CHANNEL_NAME as String)
            .setSmallIcon(com.frogobox.notification.R.drawable.ic_frogo_notif)
            .setContentTitle("You:")
            .setContentText(message)
            .build()
            .launch(notifyId)
    }
}
