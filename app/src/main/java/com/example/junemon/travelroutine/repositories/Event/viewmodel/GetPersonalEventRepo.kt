package com.example.junemon.travelroutine.repositories.Event.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.database.model.PersonalEvent

class GetPersonalEventRepo(application: Application) : AndroidViewModel(application) {
    fun LoadEventData(): LiveData<List<PersonalEvent>>? = MainApplication.mDBAccess?.personalEvent_dao()?.getLiveAllData()
}