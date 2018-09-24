package com.example.junemon.travelroutine.helper.alarmHours

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.junemon.travelroutine.helper.NotificationMaker

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationMaker.reminderNotif(p0)
        }
        NotificationMaker.reminderNotif(p0)
    }


}