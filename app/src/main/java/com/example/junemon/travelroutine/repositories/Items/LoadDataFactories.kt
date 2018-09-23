package com.example.junemon.travelroutine.repositories.Items

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.junemon.travelroutine.database.MainDatabase

class LoadDataFactories(val mDb: MainDatabase?, var taskId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadDataByIds(mDb, taskId) as T
    }
}