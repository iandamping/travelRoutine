package com.example.junemon.travelroutine.feature.routine.input

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.feature.items.input.InputActivity
import com.example.junemon.travelroutine.helper.KeyboardCloser
import com.example.junemon.travelroutine.helper.alarms.AlarmSetter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_input_routines.*
import org.jetbrains.anko.selector
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
    private var actualSelectedIconHelper: Int? = null
    private var actualSelectedColorHelper: Int? = null
    private var actualTags: String? = null
    private var departDate: Date? = null
    private var getData: PersonalRoutines? = PersonalRoutines()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_routines)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = InputRoutinePresenter(this)
        presenter.onCreate(this)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
                    tvRoutineDatesReminderViews.visibility = View.VISIBLE
                    tvRoutineDatesReminderViews.text = resources.getString(R.string.reminded_info) + " ${hours} : ${minutes}"
                }, MainApplication.hours, MainApplication.minutes, true)
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, resources.getString(R.string.cancel)) { dialogInterface, i ->
                    mTimePicker.dismiss()
                }

                mTimePicker.setTitle(resources.getString(R.string.select_time))
                mTimePicker.show()
            } else if (!b) {
                actualSelectedHour = null
                actualSelectedMinute = null
                tvRoutineDatesReminderViews.visibility = View.GONE
                tvRoutineDatesReminderViews.text = ""
            }
        }
        btnRoutineSave.setOnClickListener {
            if (etRoutine?.text.isNullOrEmpty()) {
                etRoutine.requestFocus()
                etRoutine.error = resources.getString(R.string.routine_cannot_be_empty)
            }
            if (etRoutineDescription?.text.isNullOrEmpty()) {
                etRoutineDescription.requestFocus()
                etRoutineDescription.error = resources.getString(R.string.routine_cannot_be_empty)
            }
            if (etRoutineDate?.text.isNullOrEmpty()) {
                etRoutineDate.requestFocus()
                etRoutineDate.error = resources.getString(R.string.routine_cannot_be_empty)
            } else if (!etRoutine?.text.isNullOrEmpty() && !etRoutineDescription?.text.isNullOrEmpty() && !etRoutineDate?.text.isNullOrEmpty()) {
                val dateExtract: String = etRoutineDate.text.toString().trim()
                routines = etRoutine.text.toString().trim()
                routineDescription = etRoutineDescription.text.toString().trim()
                getData?.description = routineDescription
                getData?.routine = routines
                departDate = MainApplication.dateFormat.parse(dateExtract)
                getData?.selectedDate = departDate
                getData?.selectedHour = actualSelectedHour
                getData?.selectedMinute = actualSelectedMinute
                getData?.tags = actualTags
                getData?.selectedIcon = actualSelectedIconHelper
                getData?.selectedColor = actualSelectedColorHelper
                insertData(getData)
            }

        }
        btnPickRoutineTag.setOnClickListener {
            presenter.getLiveDataAllTag(this)
        }
    }

    fun pickRemindedDate(onFocus: EditText) {
        KeyboardCloser.hideKeyboard(this)
        val datePicks = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, Year, MonthOfYear, Day ->
            onFocus.text = Editable.Factory.getInstance().newEditable(presenter.formatedDate(Year, MonthOfYear, Day))
            Observable.just(onFocus.text.toString().trim()).subscribe({ results ->
                actualSelectedDate
                etRoutineDate.error = null
            })
        }, MainApplication.years, MainApplication.month, MainApplication.days)
        datePicks.datePicker.minDate = System.currentTimeMillis() - 1000
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


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            return true;
        }
        return false;

    }

    override fun showTag(data: List<PersonalTags>?) {
        var position: Int? = null
        val dataForSelector: MutableList<String> = mutableListOf()
        Observable.fromIterable(data).doOnComplete {
            selector(resources.getString(R.string.pick_tag), dataForSelector) { dialogInterface, i ->
                Observable.fromArray(dataForSelector).subscribe { results ->
                    actualTags = results[i]
                    btnPickRoutineTag.text = results[i]
                    position = i
                }
                actualSelectedIconHelper = data?.get(position!!)?.newIcons
                actualSelectedColorHelper = data?.get(position!!)?.newColor
            }
        }.subscribe { results ->
            dataForSelector.add(results.newTags!!)
        }
//        selector(resources.getString(R.string.pick_tag), dataForSelector) { dialogInterface, i ->
//            actualTags = dataForSelector[i]
//            btnPickRoutineTag.text = dataForSelector[i]
//
//        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}