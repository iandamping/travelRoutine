package com.example.junemon.travelroutine.repositories.Routine

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.database.model.PersonalRoutines

class RoutineRepositories(application: Application) : AndroidViewModel(application) {
    var tasks: LiveData<List<PersonalRoutines>>? = null

    fun loadMainData(application: Application) {
        val mDb: MainDatabase? = MainDatabase.getInstances(this.getApplication())
        tasks = mDb?.personalRoutine_dao()?.getLiveAllData()
    }

    fun getPersonalLiveData(): LiveData<List<PersonalRoutines>>? = tasks
}