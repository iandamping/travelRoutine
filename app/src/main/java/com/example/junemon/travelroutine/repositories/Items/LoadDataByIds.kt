package com.example.junemon.travelroutine.repositories.Items

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.database.model.PersonalItems

class LoadDataByIds(val mDb: MainDatabase?, var taskId: Int) : ViewModel() {
    private var addData: LiveData<PersonalItems>? = mDb?.personal_dao()?.getLiveDataById(taskId)

    fun getAddDataById(): LiveData<PersonalItems>? {
        return addData
    }

}