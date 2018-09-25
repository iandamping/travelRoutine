package com.example.junemon.travelroutine.database

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutor(var diskIO: Executor = Executors.newSingleThreadExecutor(), var networkIO: Executor = Executors.newFixedThreadPool(3),
                       var mainThread: Executor = object : MainThreadExecutor() {}) {

    companion object {
        private var sInstance: AppExecutor? = null

        fun getInstance(): AppExecutor? {
            if (sInstance == null) {
                synchronized(AppExecutor::class) {
                    sInstance = object : AppExecutor(Executors.newSingleThreadExecutor()
                            , Executors.newFixedThreadPool(3), object : MainThreadExecutor() {}) {
                    }
                }
            }
            return sInstance
        }

    }


    open class MainThreadExecutor : Executor {
        val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

        override fun execute(p0: Runnable?) {
            mainThreadHandler.post(p0)
        }

    }
}