package com.example.junemon.travelroutine.repositories.Tags

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.database.model.PersonalTags

class GetPersonalTagRepo(application: Application) : AndroidViewModel(application) {
    var task: LiveData<List<PersonalTags>>? = null
    fun getData() {
        val mDb: MainDatabase? = MainDatabase.getInstances(this.getApplication())
        task = mDb?.personalTags_dao()?.getAllData()
    }

    fun getPersonalTagLiveData(): LiveData<List<PersonalTags>>? = task
}