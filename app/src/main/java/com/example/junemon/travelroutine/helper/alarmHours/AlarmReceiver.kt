package com.example.junemon.travelroutine.helper.alarmHours

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.helper.NotificationMaker

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        var getItemData: PersonalItems? = null
        var getRoutineData: PersonalRoutines? = null
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationMaker.reminderNotif(p0, getItemData, getRoutineData)
        }
        NotificationMaker.reminderNotif(p0, getItemData, getRoutineData)
    }


}