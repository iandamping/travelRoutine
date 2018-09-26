package com.example.junemon.travelroutine.repositories.News

import android.util.Log
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.database.AppExecutor
import com.example.junemon.travelroutine.network.model.PersonalNews
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsRepositories {

    companion object {
        private val GET_DATA: String = "Get it now"
        var STATE_OF_PULLED: String = "Pull"
        private val compose: CompositeDisposable = CompositeDisposable()

        fun getAllNews(action: String, categoriesEntertainment: String, categoriesBussiness: String, countries: String, source: String, key: String) {
            if (action.equals("Pull")) {
                dataPuller(categoriesEntertainment, categoriesBussiness, countries, source, key)
            } else if (action.equals(GET_DATA)) {
                dataPuller(categoriesEntertainment, categoriesBussiness, countries, source, key)
            }
        }

        private fun dataPuller(categoriesEntertainment: String, categoriesBussiness: String, countries: String, source: String, key: String) {
            compose.add(MainApplication.getNewsData.getTopHeadlineNewsBBC(source, key)
                    .flatMap { response ->
                        savingNewsData(response)
                        return@flatMap MainApplication.getNewsData.getTopHeadlineBussines(countries, categoriesBussiness, key)
                    }
                    .flatMap { response ->
                        savingBussinessNewsData(response)
                        return@flatMap MainApplication.getNewsData.getTopHeadlineEntertainment(countries, categoriesEntertainment, key)
                    }
                    .flatMap { response ->
                        savingEntertainmentNewsData(response)
                        return@flatMap MainApplication.getNewsData.getTopHeadlineEntertainment(countries, categoriesEntertainment, key)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ }, { error -> Log.e(MainApplication.TAG, error.localizedMessage) })
            )
            STATE_OF_PULLED = "Pulled"

        }

        private fun savingNewsData(data: PersonalNews?) {
            AppExecutor.getInstance()?.diskIO?.execute {
                MainApplication.mDBAccess?.personalNews_dao()?.deleteAllData()
                MainApplication.mDBAccess?.personalNews_dao()?.insertData(data?.articles)
            }
        }

        private fun savingBussinessNewsData(data: PersonalNewsBussines?) {
            AppExecutor.getInstance()?.diskIO?.execute {
                MainApplication.mDBAccess?.personalBussinessNews_dao()?.deleteAllData()
                MainApplication.mDBAccess?.personalBussinessNews_dao()?.insertData(data?.articles)
            }
        }

        private fun savingEntertainmentNewsData(data: PersonalNewsEntertainment?) {
            AppExecutor.getInstance()?.diskIO?.execute {
                MainApplication.mDBAccess?.personalEntertainmentNews_dao()?.deleteAllData()
                MainApplication.mDBAccess?.personalEntertainmentNews_dao()?.insertData(data?.articles)
            }
        }

        fun onDestroy() {
            if (compose != null && compose.isDisposed) {
                compose.dispose()
            }
        }
    }

}