package com.example.junemon.travelroutine

import android.annotation.SuppressLint
import android.app.Application
import android.support.customtabs.CustomTabsIntent
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.network.ApiClient
import com.maltaisn.icondialog.IconHelper
import java.text.SimpleDateFormat
import java.util.*

class MainApplication : Application() {


    companion object {
        val cal: Calendar = Calendar.getInstance()
        val TAG: String = MainApplication::javaClass.name
        private const val DATE_FORMAT: String = "dd-MM-yyy"
        @SuppressLint("ConstantLocale")
        val dateFormat: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        lateinit var builder: CustomTabsIntent
        lateinit var iconHelper: IconHelper
        var mDBAccess: MainDatabase? = null
        var MAIN_APPS: Application = Application()
        val years: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val days: Int = cal.get(Calendar.DAY_OF_MONTH)
        val hours: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cal.get(Calendar.MINUTE)
        val getNewsData by lazy {
            ApiClient.create()
        }
    }

    override fun onCreate() {
        super.onCreate()
        mDBAccess = MainDatabase.getInstances(applicationContext)
        builder = CustomTabsIntent.Builder().build()
        iconHelper = IconHelper.getInstance(this)

    }
}