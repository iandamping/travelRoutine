package com.example.junemon.travelroutine.feature.routine.input

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_output_detail_routines.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.text.DateFormat

class InputRoutineDetail : AppCompatActivity(), InputRoutineView {
    private var data: PersonalRoutines? = null
    lateinit var presenter: InputRoutinePresenter
    private var menuItem: Menu? = null
    private val df: DateFormat = MainApplication.dateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_output_detail_routines)
        presenter = InputRoutinePresenter(this)
        presenter.onCreate(this)
        initListener()
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

    override fun showData(data: PersonalRoutines?) {
        tvRoutineDetails.text = data?.routine
        tvRoutineDescriptionDetails.text = data?.description
        tvRoutineDate.text = df.format(data?.selectedDate)
        if (data?.selectedHour != null) {
            llRoutineAlarm.visibility = View.VISIBLE
            tvAlarmRoutineHours.text = "${data.selectedHour} : ${data.selectedMinute}"

        }
    }

    override fun initView() {
        val i: Intent = intent
        if (i != null && i.hasExtra(InputRoutineActivity.INPUT_ROUTINE_ITEM_KEYS)) {
            data = i.getParcelableExtra(InputRoutineActivity.INPUT_ROUTINE_ITEM_KEYS)
            presenter.getData(data)
        }
    }

    override fun initListener() {
    }
}