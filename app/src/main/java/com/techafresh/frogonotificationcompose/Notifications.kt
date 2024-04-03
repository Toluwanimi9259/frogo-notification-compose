package com.techafresh.frogonotificationcompose

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.frogobox.notification.FrogoNotification
import com.frogobox.notification.core.FrogoNotifActionRemoteInputListener
import com.frogobox.notification.core.FrogoNotifCustomContentViewListener
import com.frogobox.notification.core.FrogoNotifInboxStyleListener
import com.techafresh.frogonotificationcompose.Constants.GROUP_KEY_EMAILS
import com.techafresh.frogonotificationcompose.Constants.MAX_NOTIFICATION
import com.techafresh.frogonotificationcompose.Constants.NOTIFICATION_REQUEST_CODE
import kotlin.random.Random


var idNotification = 0

val stackNotif = ArrayList<NotificationItem>()


fun sendNormalNotification(
    context: Context,
    channelId : String,
    channelName : String,
    title : String,
    contentText : String,
    subText : String,
    importance : Int,
    smallIcon : Int = com.frogobox.notification.R.drawable.ic_frogo_notif,
    largeIcon : Int = com.frogobox.notification.R.drawable.ic_frogo_notification_apps,
    intent: PendingIntent

){
    FrogoNotification.Inject(context) // Intialize for Context
        .setChannelId(channelId) // Intialize for Channel ID
        .setChannelName(channelName) // Initialize for Channel Name
        .setContentIntent(intent) // Initialize for Content Intent
        .setSmallIcon(smallIcon) // Initialize for Small Icon
        .setLargeIcon(largeIcon) // Initialize for Large Icon
        .setContentTitle(title) // Initialize for Content Title
        .setContentText(contentText) // Initialize for Content Text
        .setSubText(subText) // Initialize for Sub Text
        .setupAutoCancel() // Initialize for Auto Cancel
        .build() // Build the Frogo Notification
        .launch(importance) // Notify the Frogo Notification
}

fun sendCustomNotification(
    context: Context,
    channelId : String,
    channelName : String,
    smallIcon : Int = com.frogobox.notification.R.drawable.ic_frogo_notif,
    importance : Int,
    intent: PendingIntent
){
    val collapsed = object : FrogoNotifCustomContentViewListener {
        override fun setupCustomView(): Int {
            return R.layout.custom_collapsed
        }

        override fun setupComponent(context: Context, customView: RemoteViews) {
            customView.apply{
                setTextViewText(R.id.text_view_collapsed_1, "Hello World!")
            }
        }
    }

    val expanded = object : FrogoNotifCustomContentViewListener {
        override fun setupCustomView(): Int {
            return R.layout.custom_expanded
        }

        override fun setupComponent(context: Context, customView: RemoteViews) {
            customView.apply {
                setImageViewResource(R.id.image_view_expanded, R.drawable.ic_launcher_foreground)
                setOnClickPendingIntent(R.id.image_view_expanded, intent)
            }
        }
    }

    FrogoNotification.Inject(context) // Intialize for Context
        .setChannelId(channelId) // Intialize for Channel ID
        .setChannelName(channelName) // Initialize for Channel Name
        .setSmallIcon(smallIcon) // Initialize for Small Icon
        .setCustomContentView(collapsed) // Collapsed Custom View
        .setCustomBigContentView(expanded) // Expanded Custom View
        .build() // Build the Frogo Notification
        .launch(importance) // Notify the Frogo Notification
}

fun sendStack(
    context: Context,
    sender: String,
    message: String
) {
    stackNotif.add(NotificationItem(idNotification, sender, message))
    sendStackNotification(context)
    idNotification++
}

fun sendStackNotification(
    context: Context,
) {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val frogoNotification = FrogoNotification.Inject(context)
        .setChannelId("Channel 03")
        .setChannelName("Stack Channel")
        .setSmallIcon(com.frogobox.notification.R.drawable.ic_frogo_email)
        .setGroup(GROUP_KEY_EMAILS)
        .setContentIntent(pendingIntent)
        .setupAutoCancel()

    // Check if NotificationID is smaller than Max Notif
    if (idNotification < MAX_NOTIFICATION) {

        stackNotif[idNotification].message?.let {
            frogoNotification
                .setContentTitle("New Email from " + stackNotif[idNotification].sender)
                .setContentText(it)
                .setLargeIcon(com.frogobox.notification.R.drawable.ic_frogo_notif)
        }

    } else {

        frogoNotification
            .setContentTitle("$idNotification new emails")
            .setContentText("mail@techafresh.com")
            .setGroupSummary()
            .setupInboxStyle(object : FrogoNotifInboxStyleListener {
                override fun addLine1(): String {
                    return "New Email from " + stackNotif[idNotification].sender
                }

                override fun addLine2(): String {
                    return "New Email from " + stackNotif[idNotification - 1].sender
                }

                override fun setBigContentTitle(): String {
                    return "$idNotification new messages"
                }

                override fun setSummaryText(): String {
                    return "mail@techafresh"
                }
            })

    }

    frogoNotification
        .build()
        .launch(idNotification)

}

fun generateRandomData() : String{
    val message = listOf(
        "Message 1",
        "Message 2",
        "Message 3",
        "Message 4",
        "Message 5",
        "Message 6",
        "Message 7",
    )
    val sender = listOf(
        "Sender 1",
        "Sender 2",
        "Sender 3",
        "Sender 4",
        "Sender 5",
        "Sender 6",
        "Sender 7",
    )
    return message[Random(1).nextInt(7)] + "," + sender[Random(1).nextInt(7)]

}


data class NotificationItem(
    var id: Int,
    var sender: String?,
    var message: String?
)
