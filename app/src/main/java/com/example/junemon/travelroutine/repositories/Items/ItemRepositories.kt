package com.example.junemon.travelroutine.repositories.Items

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.database.model.PersonalItems

class ItemRepositories(application: Application) : AndroidViewModel(application) {
    var tasks: LiveData<List<PersonalItems>>? = null

    fun loadMainData(application: Application) {
        val mDb: MainDatabase? = MainDatabase.getInstances(this.getApplication())
        tasks = mDb?.personal_dao()?.getLiveAllData()
    }

    fun getPersonalLiveData(): LiveData<List<PersonalItems>>? = tasks
}