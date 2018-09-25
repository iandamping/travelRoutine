package com.example.junemon.travelroutine.repositories.News.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines

class GetBussinesRepo(application: Application) : AndroidViewModel(application) {
    var tasks: LiveData<List<PersonalNewsBussines.Article>>? = null

    fun getData(application: Application) {
        val mDb: MainDatabase? = MainDatabase.getInstances(this.getApplication())
        tasks = mDb?.personalBussinessNews_dao()?.getLiveAllData()
    }

    fun getPersonalNewsLiveData(): LiveData<List<PersonalNewsBussines.Article>>? = tasks
}