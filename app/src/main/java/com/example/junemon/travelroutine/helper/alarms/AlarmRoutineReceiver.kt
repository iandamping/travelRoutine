package com.example.junemon.travelroutine.helper.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.helper.NotificationRoutineMaker

class AlarmRoutineReceiver : BroadcastReceiver() {
    companion object {
        var getRoutineData: PersonalRoutines? = null
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationRoutineMaker.reminderRoutineNotif(p0, AlarmRoutineReceiver.getRoutineData)
        }
        NotificationRoutineMaker.reminderRoutineNotif(p0, AlarmRoutineReceiver.getRoutineData)
    }
}