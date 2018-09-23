package com.example.junemon.travelroutine

import android.app.Application
import com.example.junemon.travelroutine.database.MainDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainApplication : Application() {


    companion object {
        private val cal: Calendar = Calendar.getInstance()
        val TAG: String = MainApplication::javaClass.name
        private const val DATE_FORMAT: String = "dd-MM-yyy"
        val dateFormat: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        var mDBAccess: MainDatabase? = null
        var MAIN_APPS: Application = Application()
        val years: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val days: Int = cal.get(Calendar.DAY_OF_MONTH)
        val hours: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cal.get(Calendar.MINUTE)
    }

    override fun onCreate() {
        super.onCreate()
        mDBAccess = MainDatabase.getInstances(applicationContext)
    }
}