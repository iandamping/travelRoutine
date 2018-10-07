package com.example.junemon.travelroutine.repositories.Routine.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.junemon.travelroutine.database.MainDatabase
import com.example.junemon.travelroutine.database.model.PersonalRoutines

class LoadRoutineByiD(val mDb: MainDatabase?, taskId: Int) : ViewModel() {
    private var addData: LiveData<PersonalRoutines>? = mDb?.personalRoutine_dao()?.getLiveDataById(taskId)

    fun getAddDataById(): LiveData<PersonalRoutines>? {
        return addData
    }
}