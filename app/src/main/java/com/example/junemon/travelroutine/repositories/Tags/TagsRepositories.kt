package com.example.junemon.travelroutine.repositories.Tags

import android.content.Context
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
        private var composite: CompositeDisposable

        init {
            composite = CompositeDisposable()
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