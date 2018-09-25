package com.example.junemon.travelroutine.feature.items.input

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.base.BasePresenters
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.repositories.Items.LoadDataByIds
import com.example.junemon.travelroutine.repositories.Items.LoadDataFactories
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InputPresenter(var mView: InputView) : BasePresenters {
    private lateinit var ctx: Context
    private var loadDataFactory: LoadDataFactories? = null
    private var loadDataById: LoadDataByIds? = null
    private lateinit var composite: CompositeDisposable
    override fun getContext(): Context? {
        return ctx
    }

    override fun onCreate(context: Context) {
        ctx = context
        mView.initView()
        mView.initListener()
        composite = CompositeDisposable()
    }

    override fun onPause() {
    }

    fun formatedDate(years: Int, months: Int, dayOfMonth: Int): String {
        val month: Int = months + 1
        var formatMonth = "" + month
        var formatDay = "" + dayOfMonth
        if (month < 10) {
            formatMonth = "0" + month
        }
        if (dayOfMonth < 10) {
            formatDay = "0" + dayOfMonth
        }
        return formatDay + "-" + formatMonth + "-" + years.toString()
    }

    fun getData(data: PersonalItems?) {
        mView.showData(data)
    }

    fun getDataById(targetActivity: FragmentActivity, targetId: Int?) {
        loadDataFactory = LoadDataFactories(MainApplication.mDBAccess, targetId!!)
        loadDataById = ViewModelProviders.of(targetActivity, loadDataFactory).get(LoadDataByIds::class.java)
        loadDataById?.getAddDataById()?.observe(targetActivity, Observer { results -> mView.showData(results) })
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
        var ID: Int? = data?.ID
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

}