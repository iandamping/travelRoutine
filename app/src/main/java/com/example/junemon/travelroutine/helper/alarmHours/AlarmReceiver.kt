package com.example.junemon.travelroutine.helper.alarmHours

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

        }
    }


}