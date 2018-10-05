package com.example.junemon.travelroutine.repositories.Tags

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.database.model.PersonalTags

class GetPersonalTagById(val mDb: MainDatabase?, var taskId: Int) : ViewModel() {
    private var addData: LiveData<PersonalTags>? = mDb?.personalTags_dao()?.getLiveDataById(taskId)

    fun getAddDataById(): LiveData<PersonalTags>? {
        return addData
    }
}