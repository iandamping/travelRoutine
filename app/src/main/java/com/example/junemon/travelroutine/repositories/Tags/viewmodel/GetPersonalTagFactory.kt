package com.example.junemon.travelroutine.repositories.Tags.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.junemon.travelroutine.database.MainDatabase

class GetPersonalTagFactory(val mDb: MainDatabase?, var taskId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetPersonalTagById(mDb, taskId) as T
    }
}