package com.example.junemon.travelroutine.helper.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import java.util.*


class AlarmSetter {
    companion object {
        fun startItemsAlarm(ctx: Context, data: PersonalItems?) {
            if (data?.selectedHour == null) {
                return
            } else {
                val cal: Calendar = Calendar.getInstance()
                cal.time = data.selectedDate
                data.selectedHour?.let { cal.set(Calendar.HOUR_OF_DAY, it) }
                data.selectedMinute?.let { cal.set(Calendar.MINUTE, it) }

                val receiver = ComponentName(ctx, AlarmReceiver::class.java)
                ctx.getPackageManager().setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
                val i = Intent(ctx, AlarmReceiver::class.java)
                val pi = PendingIntent.getBroadcast(ctx.getApplicationContext(), 0, i, FLAG_CANCEL_CURRENT)
                val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                AlarmReceiver.getItemData = data
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            }
        }

        fun startRoutineAlarm(ctx: Context, data: PersonalRoutines?) {
            if (data?.selectedHour == null) {
                return
            } else {
                val cal: Calendar = Calendar.getInstance()
                cal.time = data.selectedDate
                data.selectedHour?.let { cal.set(Calendar.HOUR_OF_DAY, it) }
                data.selectedMinute?.let { cal.set(Calendar.MINUTE, it) }

                val receiver = ComponentName(ctx, AlarmReceiver::class.java)
                ctx.getPackageManager().setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
                val i = Intent(ctx, AlarmReceiver::class.java)
                val pi = PendingIntent.getBroadcast(ctx.getApplicationContext(), 1, i, FLAG_CANCEL_CURRENT)
                val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                AlarmReceiver.getRoutineData = data
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            }
        }
    }

}