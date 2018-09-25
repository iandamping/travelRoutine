package com.example.junemon.travelroutine.repositories.News

import android.util.Log
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.database.AppExecutor
import com.example.junemon.travelroutine.network.model.PersonalNews
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsRepositories {

    companion object {
        private val GET_DATA: String = "Get it now"
        var STATE_OF_PULLED: String = "Pull"
        private var listData: List<PersonalNews.Article>? = null
        private val compose: CompositeDisposable = CompositeDisposable()

        fun getAllNews(action: String, source: String, key: String) {
            if (action.equals("Pull")) {
                dataPuller(source, key)
            } else if (action.equals(GET_DATA)) {
                dataPuller(source, key)
            }
        }

        private fun dataPuller(source: String, key: String) {
            compose.add(MainApplication.getNewsData.getTopHeadlineNews(source, key)
                    .doOnNext { results ->
                        savingData(results)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ }, { error -> Log.e("log error", error.localizedMessage) })
            )
            STATE_OF_PULLED = "Pulled"
        }

        private fun savingData(data: PersonalNews?) {
            AppExecutor.getInstance()?.diskIO?.execute {
                MainApplication.mDBAccess?.personalNews_dao()?.deleteAllData()
                MainApplication.mDBAccess?.personalNews_dao()?.insertData(data?.articles)
            }
        }
    }

}