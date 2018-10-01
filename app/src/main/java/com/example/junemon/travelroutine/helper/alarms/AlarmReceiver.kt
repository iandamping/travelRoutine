package com.example.junemon.travelroutine.helper.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.helper.NotificationMaker
import com.example.junemon.travelroutine.helper.NotificationRoutineMaker

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        var getItemData: PersonalItems? = null
        var getRoutineData: PersonalRoutines? = null
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        NotificationMaker.reminderNotif(p0, getItemData)
        NotificationRoutineMaker.reminderRoutineNotif(p0, getRoutineData)

        if (p1?.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationMaker.reminderNotif(p0, getItemData)
            NotificationRoutineMaker.reminderRoutineNotif(p0, getRoutineData)
        }

    }


}