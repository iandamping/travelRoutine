package com.example.junemon.travelroutine.repositories.Tags

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalTags
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.design.snackbar

class TagsRepositories {

    companion object {
        const val KEY = "data"
        const val DEVICE_TOKEN = "tokenizer"
        private var composite: CompositeDisposable
        lateinit var firstDatas: PersonalTags
        lateinit var secDatas: PersonalTags
        lateinit var thirdDatas: PersonalTags
        lateinit var fourthDatas: PersonalTags
        lateinit var fifthDatas: PersonalTags
        lateinit var sixthData: PersonalTags

        init {
            composite = CompositeDisposable()
        }

        @SuppressLint("CheckResult")
        fun initFirstItemTag(ctx: Context?) {
            val firstData: List<String> = listOf("Travel", "Visiting Friends", "Road Trip", "Volunteer Travel", "Bussiness Travel", "Group Tour")
            Observable.fromArray(firstData)
                    .flatMap { response -> initData(response) }
                    .subscribe {}
            val pref = ctx?.applicationContext?.getSharedPreferences(DEVICE_TOKEN, Context.MODE_PRIVATE)
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

        fun addNewTag(newTag: PersonalTags, ctx: Context?, view: View) {
            composite.add(Observable.fromCallable {
                Runnable {
                    MainApplication.mDBAccess?.personalTags_dao()?.insertData(newTag)
                }.run()
            }.subscribeOn(Schedulers.io()).subscribe {
                snackbar(view, ctx?.resources?.getString(R.string.saved)!!)
            })
        }

        fun deleteTag(deleteTag: PersonalTags, ctx: Context?, view: View) {
            composite.add(Completable.fromCallable {
                Runnable {
                    MainApplication.mDBAccess?.personalTags_dao()?.deleteData(deleteTag)
                }.run()
            }.subscribeOn(Schedulers.io()).subscribe {
                snackbar(view, ctx?.resources?.getString(R.string.deleted)!!)
            })

        }

        fun updateTag(updateTag: PersonalTags, ctx: Context?, view: View) {
            composite.add(Observable.fromCallable {
                Runnable {
                    MainApplication.mDBAccess?.personalTags_dao()?.updateData(updateTag)
                }.run()
            }.subscribeOn(Schedulers.io()).subscribe { snackbar(view, ctx?.resources?.getString(R.string.updated)!!) })

        }
    }


}