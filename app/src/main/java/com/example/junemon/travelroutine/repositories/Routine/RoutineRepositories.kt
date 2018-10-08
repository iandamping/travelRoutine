package com.example.junemon.travelroutine.repositories.Routine

import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RoutineRepositories {

    private var composite: CompositeDisposable

    init {
        composite = CompositeDisposable()
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