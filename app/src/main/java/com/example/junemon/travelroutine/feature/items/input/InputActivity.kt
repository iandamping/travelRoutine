package com.example.junemon.travelroutine.feature.items.input

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.EditText
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.MainApplication.Companion.days
import com.example.junemon.travelroutine.MainApplication.Companion.hours
import com.example.junemon.travelroutine.MainApplication.Companion.minutes
import com.example.junemon.travelroutine.MainApplication.Companion.month
import com.example.junemon.travelroutine.MainApplication.Companion.years
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.helper.KeyboardCloser
import com.example.junemon.travelroutine.helper.ValidateEditTextHelper
import com.example.junemon.travelroutine.helper.alarmHours.AlarmSetter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_input_items.*
import java.util.*


class InputActivity : AppCompatActivity(), InputView {
    companion object {
        val INPUT_ITEMS_ACTIVITY_KEY: String = "this is key"
    }

    private val DEFAULT_TASK_ID = -1
    private var mTaskId = DEFAULT_TASK_ID
    private lateinit var presenter: InputPresenter
    private var itemName: String? = null
    private var destinantion: String? = null
    private var actualSelectedDate: String? = null
    private var actualSelectedHour: Int? = null
    private var actualSelectedMinute: Int? = null
    private var departDate: Date? = null
    private var getData: PersonalItems? = PersonalItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_items)
        presenter = InputPresenter(this)
        presenter.onCreate(this)
        initView()

    }

    override fun showData(data: PersonalItems?) {
        etBarang.text = Editable.Factory.getInstance().newEditable(data?.items)
        etDestination.text = Editable.Factory.getInstance().newEditable(data?.destination)
        etDepartDates.text = Editable.Factory.getInstance().newEditable(dateFormat.format(data?.selectedDate))
    }

    override fun initView() {
//        initListener()
        val i: Intent = intent
        if (i != null && i.hasExtra(INPUT_ITEMS_ACTIVITY_KEY)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                showData(i.getParcelableExtra(INPUT_ITEMS_ACTIVITY_KEY))

            }
        }
    }

    override fun initListener() {
        etDepartDates.setOnClickListener { view -> pickRemindedDate(etDepartDates) }
        swDepartReminder.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hours, minutes ->
                    Observable.just(hours).subscribe { results -> actualSelectedHour = results }
                    Observable.just(minutes).subscribe { results -> actualSelectedMinute = results }
                    etDepartDateslReminderViews.visibility = View.VISIBLE
                    etDepartDateslReminderViews.text = Editable.Factory.getInstance()
                            .newEditable(resources.getString(R.string.reminded_info) + " ${hours} : ${minutes}")
                }, hours, minutes, true)
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialogInterface, i ->
                    mTimePicker.dismiss()
                }

                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            } else if (!b) {
                actualSelectedHour = null
                actualSelectedMinute = null
                etDepartDateslReminderViews.visibility = View.GONE
                etDepartDateslReminderViews.text = Editable.Factory.getInstance()
                        .newEditable("")
            }
        }

        btnSave.setOnClickListener {
            if (!ValidateEditTextHelper.validates(this, etDestination, ValidateEditTextHelper.Type.EMPTY)) {
                destinantion = etDestination.text.toString().trim()
                getData?.destination = destinantion
            }
            if (!ValidateEditTextHelper.validates(this, etBarang, ValidateEditTextHelper.Type.EMPTY)) {
                itemName = etBarang.text.toString().trim()
                getData?.items = itemName

            }
            if (!ValidateEditTextHelper.validates(this, etDepartDates, ValidateEditTextHelper.Type.EMPTY)) {
                var dateExtract: String = etDepartDates.text.toString().trim()
                departDate = dateFormat.parse(dateExtract)
                getData?.selectedDate = departDate
            }
            getData?.selectedHour = actualSelectedHour
            getData?.selectedMinute = actualSelectedMinute
            insertData(getData)

        }


    }

    fun pickRemindedDate(onFocus: EditText) {
        KeyboardCloser.hideKeyboard(this)
        val datePicks = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, Year, MonthOfYear, Day ->
            onFocus.text = Editable.Factory.getInstance().newEditable(presenter.formatedDate(Year, MonthOfYear, Day))
            Observable.just(onFocus.text.toString().trim()).subscribe({ results -> actualSelectedDate })
        }, years, month, days)
        datePicks.show()
    }

    fun insertData(data: PersonalItems?) {
        presenter.insertData(data)
        AlarmSetter.startItemsAlarm(this, data)
        finish()
        clearEditText()
    }

    fun clearEditText() {
        etDestination.text = Editable.Factory.getInstance().newEditable("")
        etBarang.text = Editable.Factory.getInstance().newEditable("")
        etDepartDates.text = Editable.Factory.getInstance().newEditable("")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.finishObserving()
    }
}