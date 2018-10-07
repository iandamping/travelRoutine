package com.example.junemon.travelroutine.repositories.Routine.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.junemon.travelroutine.database.MainDatabase

class LoadRoutineFactories(val mDb: MainDatabase, var taskId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadRoutineByiD(mDb, taskId) as T
    }
}