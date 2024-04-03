package com.techafresh.frogonotificationcompose.reply

import android.content.Context
import android.content.Intent
import com.techafresh.frogonotificationcompose.reply.NotificationService.Companion.REPLY_ACTION

class ReplyActivity {
    companion object {
        private const val KEY_MESSAGE_ID = "key_message_id"
        private const val KEY_NOTIFY_ID = "key_notify_id"

        fun getReplyMessageIntent(context: Context, notifyId: Int, messageId: Int): Intent {
            val intent = Intent(context, ReplyActivity::class.java)
            intent.action = REPLY_ACTION
            intent.putExtra(KEY_MESSAGE_ID, messageId)
            intent.putExtra(KEY_NOTIFY_ID, notifyId)
            return intent
        }

    }
}
