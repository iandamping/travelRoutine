package com.example.junemon.travelroutine.feature.items.input

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalTags
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_output_detail_items)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = InputPresenter(this)
        presenter.onCreate(this)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun showTag(data: List<PersonalTags>?) {
        
    }

}