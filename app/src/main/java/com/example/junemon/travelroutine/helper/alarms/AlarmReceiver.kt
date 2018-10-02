package com.example.junemon.travelroutine.helper.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.helper.NotificationMaker

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        var getItemData: PersonalItems? = null
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationMaker.reminderNotif(p0, getItemData)
        }
        NotificationMaker.reminderNotif(p0, getItemData)
    }


}