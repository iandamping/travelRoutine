package com.example.junemon.travelroutine.feature.routine.input

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.EditText
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.feature.items.input.InputActivity
import com.example.junemon.travelroutine.helper.KeyboardCloser
import com.example.junemon.travelroutine.helper.ValidateEditTextHelper
import com.example.junemon.travelroutine.helper.alarms.AlarmSetter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_input_routines.*
import java.util.*

class InputRoutineActivity : AppCompatActivity(), InputRoutineView {

    companion object {
        val INPUT_ROUTINE_ITEM_KEYS: String = "this was key"
    }

    private val DEFAULT_TASK_ID = -1
    private var mTaskId = DEFAULT_TASK_ID
    private lateinit var presenter: InputRoutinePresenter
    private var routineDescription: String? = null
    private var routines: String? = null
    private var actualSelectedDate: String? = null
    private var actualSelectedHour: Int? = null
    private var actualSelectedMinute: Int? = null
    private var departDate: Date? = null
    private var getData: PersonalRoutines? = PersonalRoutines()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_routines)
        presenter = InputRoutinePresenter(this)
        presenter.onCreate(this)
        initView()
    }

    override fun showData(data: PersonalRoutines?) {
        etRoutine.text = Editable.Factory.getInstance().newEditable(data?.routine)
        etRoutineDate.text = Editable.Factory.getInstance().newEditable(MainApplication.dateFormat.format(data?.selectedDate))
        etRoutineDescription.text = Editable.Factory.getInstance().newEditable(data?.description)
    }

    override fun initView() {
        val i: Intent = intent
        if (i != null && i.hasExtra(InputActivity.INPUT_ITEMS_ACTIVITY_KEY)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                showData(i.getParcelableExtra(InputRoutineActivity.INPUT_ROUTINE_ITEM_KEYS))
            }
        }
    }

    override fun initListener() {
        etRoutineDate.setOnClickListener { view -> pickRemindedDate(etRoutineDate) }

        swRoutineReminder.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hours, minutes ->
                    Observable.just(hours).subscribe { results -> actualSelectedHour = results }
                    Observable.just(minutes).subscribe { results -> actualSelectedMinute = results }
                    etRoutineDatesReminderViews.visibility = View.VISIBLE
                    etRoutineDatesReminderViews.text = Editable.Factory.getInstance()
                            .newEditable(resources.getString(R.string.reminded_info) + " ${hours} : ${minutes}")
                }, MainApplication.hours, MainApplication.minutes, true)
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialogInterface, i ->
                    mTimePicker.dismiss()
                }

                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            } else if (!b) {
                actualSelectedHour = null
                actualSelectedMinute = null
                etRoutineDatesReminderViews.visibility = View.GONE
                etRoutineDatesReminderViews.text = Editable.Factory.getInstance()
                        .newEditable("")
            }
        }
        btnRoutineSave.setOnClickListener {
            if (!ValidateEditTextHelper.validates(this, etRoutine, ValidateEditTextHelper.Type.EMPTY)) {
                routines = etRoutine.text.toString().trim()
                getData?.routine = routines
            }
            if (!ValidateEditTextHelper.validates(this, etRoutineDescription, ValidateEditTextHelper.Type.EMPTY)) {
                routineDescription = etRoutineDescription.text.toString().trim()
                getData?.description = routineDescription

            }
            if (!ValidateEditTextHelper.validates(this, etRoutineDate, ValidateEditTextHelper.Type.EMPTY)) {
                var dateExtract: String = etRoutineDate.text.toString().trim()
                departDate = MainApplication.dateFormat.parse(dateExtract)
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
        }, MainApplication.years, MainApplication.month, MainApplication.days)
        datePicks.show()
    }

    fun insertData(data: PersonalRoutines?) {
        presenter.insertData(data)
        AlarmSetter.startRoutineAlarm(this, data)
        finish()
        clearEditText()
    }

    fun clearEditText() {
        etRoutine.text = Editable.Factory.getInstance().newEditable("")
        etRoutineDate.text = Editable.Factory.getInstance().newEditable("")
        etRoutineDescription.text = Editable.Factory.getInstance().newEditable("")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.finishObserving()
    }

}