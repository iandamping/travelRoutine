package com.example.junemon.travelroutine.feature.tags

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.support.v4.app.Fragment
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.base.BaseFragmentsPresenter
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.repositories.Tags.viewmodel.GetPersonalTagRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class TagsPresenter(var mView: TagsView) : BaseFragmentsPresenter {
    var ctx: Context? = null
    lateinit var firstDatas: PersonalTags
    lateinit var secDatas: PersonalTags
    lateinit var thirdDatas: PersonalTags
    lateinit var fourthDatas: PersonalTags
    lateinit var fifthDatas: PersonalTags
    lateinit var sixthData: PersonalTags
    private var viewModel: GetPersonalTagRepo? = null

    companion object {
        const val KEY = "data"
        const val DEVICE_TOKEN = "tokenizer"
    }

    override fun onAttach(context: Context?) {
        this.ctx = context
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getLiveDataAllTag(frag: Fragment) {
        viewModel = ViewModelProviders.of(frag).get(GetPersonalTagRepo::class.java)
        viewModel?.getData()
        viewModel?.getPersonalTagLiveData()?.observe(frag, Observer { results ->
            mView.getData(results)
        })
    }

    @SuppressLint("CheckResult")
    fun initFirstItemTag(ctx: Context?) {
        val firstData: List<String> = listOf("Travel", "Visiting Friends", "Road Trip", "Volunteer Travel", "Bussiness Travel", "Group Tour")
        Observable.fromArray(firstData)
                .flatMap { response -> initData(response) }
                .subscribe {}
        val pref = ctx?.applicationContext?.getSharedPreferences(DEVICE_TOKEN, MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt(KEY, 12)
        editor?.apply()

    }


    @SuppressLint("CheckResult")
    private fun initData(firstData: List<String>): Observable<List<String>> {
        firstDatas = PersonalTags(null, firstData.get(0), R.drawable.ic_style_black_24dp, Color.BLUE)
        secDatas = PersonalTags(null, firstData.get(1), R.drawable.ic_child_friendly_black_24dp, Color.WHITE)
        thirdDatas = PersonalTags(null, firstData.get(2), R.drawable.ic_colorize_black_24dp, Color.YELLOW)
        fourthDatas = PersonalTags(null, firstData.get(3), R.drawable.ic_motorcycle_black_24dp, Color.CYAN)
        fifthDatas = PersonalTags(null, firstData.get(4), R.drawable.ic_next_week_black_24dp, Color.GREEN)
        sixthData = PersonalTags(null, firstData.get(5), R.drawable.ic_rowing_black_24dp, Color.MAGENTA)
        Observable.fromCallable {
            Runnable {
                MainApplication.mDBAccess?.personalTags_dao()?.insertData(firstDatas)
                MainApplication.mDBAccess?.personalTags_dao()?.insertData(secDatas)
                MainApplication.mDBAccess?.personalTags_dao()?.insertData(thirdDatas)
                MainApplication.mDBAccess?.personalTags_dao()?.insertData(fourthDatas)
                MainApplication.mDBAccess?.personalTags_dao()?.insertData(fifthDatas)
                MainApplication.mDBAccess?.personalTags_dao()?.insertData(sixthData)
            }.run()
        }.subscribeOn(Schedulers.io()).subscribe { }
        return Observable.just(firstData)
    }
}