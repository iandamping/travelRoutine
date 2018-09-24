package com.example.junemon.travelroutine.feature.items.input

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_output_detail_items.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.text.DateFormat

class InputDetail : AppCompatActivity(), InputView {
    private var data: PersonalItems? = null
    lateinit var presenter: InputPresenter
    private var menuItem: Menu? = null
    private val df: DateFormat = MainApplication.dateFormat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_output_detail_items)
        presenter = InputPresenter(this)
        presenter.onCreate(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.deleteMenu -> {
                alert("Are you sure want to delete ? ") {
                    yesButton {
                        Observable.just(data).subscribe({ results -> presenter.deleteData(results) })
                        finish()
                    }
                    noButton { }
                }.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showData(data: PersonalItems?) {
        tvTujuan.text = data?.destination
        tvBarang.text = data?.items
        tvDepart.text = df.format(data?.selectedDate)
        if (data?.selectedHour != null) {
            llAlarm.visibility = View.VISIBLE
            tvAlarmHours.text = "${data.selectedHour} : ${data.selectedMinute}"
        }
    }


    override fun initView() {
        val i: Intent = intent
        if (i != null && i.hasExtra(InputActivity.INPUT_ITEMS_ACTIVITY_KEY)) {
            data = i.getParcelableExtra(InputActivity.INPUT_ITEMS_ACTIVITY_KEY)
            presenter.getData(data)
        }
    }

    override fun initListener() {

    }
}