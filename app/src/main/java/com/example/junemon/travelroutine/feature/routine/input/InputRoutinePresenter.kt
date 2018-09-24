package com.example.junemon.travelroutine.feature.routine.input

import android.content.Context
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.base.BasePresenters
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InputRoutinePresenter(var mView: InputRoutineView) : BasePresenters {
    private lateinit var ctx: Context
    private lateinit var composite: CompositeDisposable

    override fun getContext(): Context? {
        return ctx
    }

    override fun onCreate(context: Context) {
        this.ctx = context
        mView.initView()
        mView.initListener()
        composite = CompositeDisposable()
    }

    fun formatedDate(years: Int, months: Int, dayOfMonth: Int): String {
        val month: Int = months + 1
        var formatMonth = "" + month
        var formatDay = "" + dayOfMonth
        if (month < 10) {
            formatMonth = "0" + month
        }
        if (dayOfMonth < 10) {
            formatDay = "0" + dayOfMonth
        }
        return formatDay + "-" + formatMonth + "-" + years.toString()
    }

    override fun onPause() {
    }

    fun getData(data: PersonalRoutines?) {
        mView.showData(data)
    }

    fun insertData(data: PersonalRoutines?) {
        composite.add(Observable.fromCallable {
            Runnable {
                MainApplication.mDBAccess?.personalRoutine_dao()?.insertData(data)
            }.run()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
        })
    }

    fun deleteData(data: PersonalRoutines?) {
        var ID: Int? = data?.ID
        composite.add(Observable.fromCallable {
            Runnable {
                MainApplication.mDBAccess?.personalRoutine_dao()?.deleteData(data)
            }.run()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
        })
    }

    fun finishObserving() {
        composite.clear()
    }
}