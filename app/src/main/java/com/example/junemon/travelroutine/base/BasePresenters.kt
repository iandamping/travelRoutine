package com.example.junemon.travelroutine.base

import android.content.Context

interface BasePresenters {
    fun getContext(): Context?

    fun onCreate(context: Context)

    fun onPause()

}