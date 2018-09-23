package com.example.junemon.travelroutine.feature.input

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
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_input.*
import java.util.*


class InputActivity : AppCompatActivity(), InputView {
    companion object {
        val INPUT_ACTIVITY_KEY: String = "this is key"
    }

    private val DEFAULT_TASK_ID = -1
    private var mTaskId = DEFAULT_TASK_ID
    private lateinit var presenter: InputPresenter
    private var itemName: String? = null
    private var destinantion: String? = null
    private var actualDepartDate: String? = null
    private var actualArrivalDate: String? = null
    private var arrivalActualHour: Int? = null
    private var arrivalActualMinutes: Int? = null
    private var departActualHour: Int? = null
    private var departActualMinutes: Int? = null
    private var departDate: Date? = null
    private var arrivalDate: Date? = null
    private var states: Boolean = false
    private var getData: PersonalItems? = PersonalItems()
    private lateinit var composite: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        presenter = InputPresenter(this)
        presenter.onCreate(this)
        composite = CompositeDisposable()
        initView()

    }

    override fun showData(data: PersonalItems?) {
        etBarang.text = Editable.Factory.getInstance().newEditable(data?.items)
        etDestination.text = Editable.Factory.getInstance().newEditable(data?.destination)
        etArrivalDates.text = Editable.Factory.getInstance().newEditable(dateFormat.format(data?.arrivalDate))
        etDepartDates.text = Editable.Factory.getInstance().newEditable(dateFormat.format(data?.departureDate))
        states = true
    }

    override fun initView() {
        initListener()
        val i: Intent = intent
        if (i != null && i.hasExtra(INPUT_ACTIVITY_KEY)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                showData(i.getParcelableExtra(INPUT_ACTIVITY_KEY))

            }
        }
    }

    override fun initListener() {

        etDepartDates.setOnClickListener { view -> pickDepartDate(etDepartDates) }
        etArrivalDates.setOnClickListener { view -> pickArrivalDate(etArrivalDates) }
        swDepartReminder.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hours, minutes ->
                    Observable.just(hours).subscribe { results -> departActualHour = results }
                    Observable.just(minutes).subscribe { results -> departActualMinutes = results }
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
                departActualHour = null
                departActualMinutes = null
                etDepartDateslReminderViews.visibility = View.GONE
                etDepartDateslReminderViews.text = Editable.Factory.getInstance()
                        .newEditable("")
            }
        }

        swArrivalReminder.setOnCheckedChangeListener { compoundButton, status ->
            if (status) {
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, TpHours, TpMinutes ->
                    Observable.just(TpHours).subscribe { results -> arrivalActualHour = results }
                    Observable.just(TpMinutes).subscribe { results -> arrivalActualMinutes = results }
                    etArrivalReminderViews.visibility = View.VISIBLE
                    etArrivalReminderViews.text = Editable.Factory.getInstance()
                            .newEditable(resources.getString(R.string.reminded_info) + " ${TpHours} : ${TpMinutes}")
                }, hours, minutes, true)

//                mTimePicker = TimePickerDialog(this, { timePicker, hours, minutes ->
//                    Observable.just(hours).subscribe { results -> getData?.arrivalSelectedHour }
//                    Observable.just(minutes).subscribe { results -> getData?.arrivalSelectedMinute }
//                }, hours, minutes, true)
//                mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, "Oke") { dialogInterface, i ->
//                }
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialogInterface, i ->
                    mTimePicker.dismiss()
                }

                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            } else if (!status) {
                arrivalActualHour = null
                arrivalActualMinutes = null
                etArrivalReminderViews.visibility = View.GONE
                etArrivalReminderViews.text = Editable.Factory.getInstance()
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
            if (!ValidateEditTextHelper.validates(this, etArrivalDates, ValidateEditTextHelper.Type.EMPTY)) {
                var dateExtract: String = etArrivalDates.text.toString().trim()
                arrivalDate = dateFormat.parse(dateExtract)
                getData?.arrivalDate = arrivalDate
            }
            if (!ValidateEditTextHelper.validates(this, etDepartDates, ValidateEditTextHelper.Type.EMPTY)) {
                var dateExtract: String = etDepartDates.text.toString().trim()
                departDate = dateFormat.parse(dateExtract)
                getData?.departureDate = departDate
            }
            getData?.arrivalSelectedHour = arrivalActualHour
            getData?.arrivalSelectedMinute = arrivalActualMinutes
            getData?.departSelectedHour = departActualHour
            getData?.departSelectedMinute = departActualMinutes
            insertData(getData)

        }


    }

    fun pickArrivalDate(onFocus: EditText) {
        KeyboardCloser.hideKeyboard(this)
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, Year, MonthOfYear, Day ->
            onFocus.text = Editable.Factory.getInstance().newEditable(presenter.formatedDate(Year, MonthOfYear, Day))
            Observable.just(onFocus.text.toString().trim()).subscribe({ results -> actualArrivalDate })
        }, years, month, days)
        datePicker.show()
    }

    fun pickDepartDate(onFocus: EditText) {
        KeyboardCloser.hideKeyboard(this)
        val datePicks = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, Year, MonthOfYear, Day ->
            onFocus.text = Editable.Factory.getInstance().newEditable(presenter.formatedDate(Year, MonthOfYear, Day))
            Observable.just(onFocus.text.toString().trim()).subscribe({ results -> actualDepartDate })
        }, years, month, days)
        datePicks.show()
    }

    fun insertData(data: PersonalItems?) {
        presenter.insertData(data)
        finish()
        clearEditText()
    }

    fun clearEditText() {
        etDestination.text = Editable.Factory.getInstance().newEditable("")
        etBarang.text = Editable.Factory.getInstance().newEditable("")
        etArrivalDates.text = Editable.Factory.getInstance().newEditable("")
        etDepartDates.text = Editable.Factory.getInstance().newEditable("")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.finishObserving()
    }
}