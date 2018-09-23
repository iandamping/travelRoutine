package com.example.junemon.travelroutine.feature.input

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import kotlinx.android.synthetic.main.item_output_detail.*
import java.text.DateFormat

class InputDetail : AppCompatActivity(), InputView {
    private var data: PersonalItems? = null
    lateinit var presenter: InputPresenter
    private val df: DateFormat = MainApplication.dateFormat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_output_detail)
        presenter = InputPresenter(this)
        presenter.onCreate(this)
        initListener()
    }

    override fun showData(data: PersonalItems?) {
        tvTujuan.text = data?.destination ?: "no data yet"
        tvBarang.text = data?.items ?: "no data yet"
        tvArrival.text = df.format(data?.arrivalDate) ?: "no data yet"
        tvDepart.text = df.format(data?.departureDate) ?: "no data yet"
        if (data?.arrivalSelectedHour == 0) {
            llAlarm.visibility = View.VISIBLE
            tvAlarmHours.text = "${data.arrivalSelectedHour} : ${data.arrivalSelectedMinute}"
        }
    }

    override fun initView() {
        val i: Intent = intent
        if (i != null && i.hasExtra(InputActivity.INPUT_ACTIVITY_KEY)) {
            data = i.getParcelableExtra(InputActivity.INPUT_ACTIVITY_KEY)
            presenter.getDataById(this, data?.ID)
        }
    }

    override fun initListener() {
//        fabEdit.setOnClickListener {
//            startActivity(intentFor<InputActivity>(InputActivity.INPUT_ACTIVITY_KEY to data))
//        }
    }
}