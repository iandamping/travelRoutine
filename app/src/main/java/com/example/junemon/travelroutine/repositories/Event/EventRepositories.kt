package com.example.junemon.travelroutine.repositories.Event

import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.database.model.PersonalEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EventRepositories {
    private lateinit var composite: CompositeDisposable

    init {
        composite = CompositeDisposable()
    }

    fun insertData(data: PersonalEvent?) {
        composite.add(Observable.fromCallable {
            Runnable {
                MainApplication.mDBAccess?.personalEvent_dao()?.insertData(data)
            }.run()
        }.subscribeOn(Schedulers.io()).subscribe { })
    }

    fun deleteData(data: PersonalEvent?) {
        composite.add(Observable.fromCallable {
            Runnable {
                MainApplication.mDBAccess?.personalEvent_dao()?.deleteData(data)
            }.run()
        }.subscribeOn(Schedulers.io()).subscribe {})
    }

    fun finishObserving() {
        composite.clear()
    }
}