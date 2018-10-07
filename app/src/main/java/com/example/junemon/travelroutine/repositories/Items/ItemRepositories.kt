package com.example.junemon.travelroutine.repositories.Items

import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.repositories.Items.viewmodel.LoadDataByIds
import com.example.junemon.travelroutine.repositories.Items.viewmodel.LoadDataFactories
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ItemRepositories {
    companion object {
        private var composite: CompositeDisposable
        private var loadDataFactory: LoadDataFactories? = null
        private var loadDataById: LoadDataByIds? = null

        init {
            composite = CompositeDisposable()
        }

        fun insertData(data: PersonalItems?) {
            composite.add(Observable.fromCallable {
                Runnable {
                    MainApplication.mDBAccess?.personalItem_dao()?.insertData(data)
                }.run()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            })
        }

        fun updateData(data: PersonalItems?) {
            composite.add(Observable.fromCallable {
                Runnable {
                    MainApplication.mDBAccess?.personalItem_dao()?.insertData(data)
                }.run()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            })
        }

        fun deleteData(data: PersonalItems?) {
            composite.add(Observable.fromCallable {
                Runnable {
                    MainApplication.mDBAccess?.personalItem_dao()?.deleteData(data)
                }.run()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            })
        }

        fun finishObserving() {
            composite.clear()
        }


//        fun getDataById(targetActivity: FragmentActivity, targetId: Int?) {
//            loadDataFactory = LoadDataFactories(MainApplication.mDBAccess, targetId!!)
//            loadDataById = ViewModelProviders.of(targetActivity, loadDataFactory).get(LoadDataByIds::class.java)
//            loadDataById?.getAddDataById()?.observe(targetActivity, Observer { results -> mView.showData(results) })
//        }
    }
}