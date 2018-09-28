package com.example.junemon.travelroutine.helper.networkchecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder


class NetworkChangeListener : BroadcastReceiver() {
    var internetAvailable: Boolean = false
    var networkChangeReceiver: BroadcastReceiver? = null

    override fun peekService(myContext: Context?, service: Intent?): IBinder {
        return super.peekService(myContext, service)
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        getInternetStatus()
    }

    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        internetAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected
        return internetAvailable
    }

    fun getInternetStatus(): Boolean {
        return this.internetAvailable
    }

    fun register(context: Context) {
        context.registerReceiver(networkChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(networkChangeReceiver)

    }

}