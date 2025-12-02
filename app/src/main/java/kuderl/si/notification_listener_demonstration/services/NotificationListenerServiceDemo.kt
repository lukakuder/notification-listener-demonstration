package kuderl.si.notification_listener_demonstration.services

import android.app.Notification
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListenerServiceDemo : NotificationListenerService()  {
    private val acceptedPackages = listOf(
        "com.hrc.eb.mobile.android.hibismobiledh",
        "com.google.android.apps.messaging"
    )

    override fun onNotificationPosted(sbn: StatusBarNotification, rankingMap: RankingMap) {
        val notification: Notification = sbn.notification
        val extras: Bundle = notification.extras

        if (sbn.packageName !in acceptedPackages) {
            //cancelNotification(sbn.key)
            Log.d(TAG, "Notification that is not in acceptedPackages ignored automatically: ${sbn.packageName}")
            return
        }

        val title = extras.getString(Notification.EXTRA_TITLE)
        val bigTitle = extras.getCharSequence(Notification.EXTRA_TITLE_BIG)?.toString()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString()

        Log.d(TAG, "Notification Posted from: ${sbn.packageName}")
        Log.d(TAG, "Title: $title")
        Log.d(TAG, "BigTitle: $bigTitle")
        Log.d(TAG, "Text: $text")
        Log.d(TAG, "BigText: $bigText")
        Log.d(TAG, "SubText: $subText")

        val ongoing = sbn.isOngoing
        Log.d(TAG, "Ongoing notification: $ongoing from ${sbn.packageName}")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d(TAG, "Notification Removed: ${sbn.packageName}")
    }

    override fun onNotificationRankingUpdate(rankingMap: RankingMap) {
        val orderedKeys = rankingMap.orderedKeys
        if (orderedKeys != null) {
            for ((i, key) in orderedKeys.withIndex()) {
                Log.d(TAG, "Notification n. $i: $key")
            }
        }
    }

    companion object {
        private const val TAG = "NotifDemoService"
    }
}