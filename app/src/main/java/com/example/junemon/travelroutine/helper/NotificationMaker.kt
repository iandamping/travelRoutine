package com.example.junemon.travelroutine.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.example.junemon.travelroutine.MainActivity
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalRoutines

class NotificationMaker {


    companion object {
        private val NOTIFICATION = "notification"
        private val REMINDER_NOTIFICATION_ID = 1138
        private val OREO_NOTIF_CHANEL_ID = "oreo-notif"
        private val PENDING_INTENT_ID = 3417
        private var contentTitle: String? = null
        private var contentText: String? = null

        fun reminderNotif(context: Context?, getItemData: PersonalItems?, getRoutineData: PersonalRoutines?) {
            val notif = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (getItemData == null) {
                contentTitle = context.resources.getString(R.string.routine_reminder)
                contentText = getRoutineData?.description
            } else if (getRoutineData == null) {
                contentTitle = context.resources.getString(R.string.item_reminder)
                contentText = getItemData.items
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(OREO_NOTIF_CHANEL_ID, NOTIFICATION, NotificationManager.IMPORTANCE_HIGH)
                notif.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(context, OREO_NOTIF_CHANEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentTitle("Hai ! ${contentTitle} okaay ?")
                    .setSmallIcon(R.drawable.ic_cofee_bean)
                    .setContentText("These ${contentText} must be important right ?")
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(contentIntent(context))  //ini pendingIntent
                    .setAutoCancel(true)  //notif akan hilang setelah di klik

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                builder.priority = NotificationCompat.PRIORITY_HIGH
            }
            notif.notify(REMINDER_NOTIFICATION_ID, builder.build())

        }


        private fun contentIntent(context: Context): PendingIntent {
            val i = Intent(context, MainActivity::class.java)
            return PendingIntent.getActivity(context, PENDING_INTENT_ID, i, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }


}