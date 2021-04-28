package com.vsnapnewschool.voicesnapmessenger.FcmServices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vsnapnewschool.voicesnapmessenger.Activities.MenuScreen
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*

@Suppress("DEPRECATION")
class MyFirebaseMessagingService : FirebaseMessagingService() {
    var notification: Notification? = null
    var notificationManager: NotificationManager? = null
    val NotificationID = 1005
    var mBuilder: NotificationCompat.Builder? = null
    var NOTIFICATION_ID = 100

    override fun onNewToken(token: String) {
        Log.i("NotificationsService", "Token was updated 🔒")
        // Overriding this method is optional.
        // You can use this token to integrate with other push notification services
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data["body"] != null) {
            createNotification(remoteMessage.data["body"]!!, remoteMessage.data["title"]!!)
        }
    }

    private fun createNotification(messageBody: String, title: String) {

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mBuilder = NotificationCompat.Builder(applicationContext, "notify_001")
        val pic = BitmapFactory.decodeResource(resources, R.drawable.event3)
        var remoteViews = RemoteViews(packageName, R.layout.exapnded_one)
        remoteViews.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))
        remoteViews.setTextViewText(R.id.content_title, title)
        remoteViews.setTextViewText(R.id.content_text, messageBody)
        var remoteViewSmall = RemoteViews(packageName, R.layout.notification_design)
        remoteViewSmall.setTextViewText(R.id.timestamp,DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME)
        )
        mBuilder!!.setSmallIcon(R.drawable.app_icon)
        mBuilder!!.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.app_icon))
        mBuilder!!.setContentTitle(title)
        mBuilder!!.setContentText(messageBody)
        mBuilder!!.setStyle(NotificationCompat.BigTextStyle().bigText(title))
        mBuilder!!.setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
        mBuilder!!.setDefaults(Notification.DEFAULT_ALL)
        mBuilder!!.setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, MenuScreen::class.java), 0))
        mBuilder!!.setAutoCancel(true)
        mBuilder!!.setContent(remoteViews)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channelId = "channel_id"
            val channel = NotificationChannel(channelId, "channel name", importance)
            channel.enableVibration(true)
            notificationManager!!.createNotificationChannel(channel)
            mBuilder!!.setChannelId(channelId)
            NOTIFICATION_ID = (Date().time / 1000L % Integer.MAX_VALUE).toInt()
            notification = mBuilder!!.build()
            notificationManager!!.notify(NotificationID, notification)

        } else {
            mBuilder = NotificationCompat.Builder(applicationContext, "MCHID")
            val notificationLayout  = RemoteViews(packageName, R.layout.exapnded_one)
            notificationLayout .setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))
            notificationLayout .setTextViewText(R.id.content_title, title)
            notificationLayout .setTextViewText(R.id.content_text, messageBody)
            var remoteVieSmall = RemoteViews(packageName, R.layout.notification_design)
            remoteVieSmall.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))

            val intent = Intent(this, MenuScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val channelId = getString(R.string.app_name)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.app_icon))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigTextStyle().bigText(title))
                .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContent(notificationLayout)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(false)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }
    }
}