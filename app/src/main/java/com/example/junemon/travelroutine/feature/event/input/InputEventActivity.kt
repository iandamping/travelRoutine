package com.example.junemon.travelroutine.feature.event.input

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
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.helper.KeyboardCloser
import com.maltaisn.icondialog.Icon
import com.maltaisn.icondialog.IconDialog
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_input_event.*
import kotlinx.android.synthetic.main.activity_input_items.*
import org.jetbrains.anko.selector
import java.util.*

class InputEventActivity : AppCompatActivity(), InputEventView, IconDialog.Callback {

    companion object {
        val INPUT_EVENT_ACTIVITY_KEY: String = "this is key"
    }

    private var selectedIcons: Array<Icon>? = null
    private var iconDialog = IconDialog()
    private val DEFAULT_TASK_ID = -1
    private var mTaskId = DEFAULT_TASK_ID
    private lateinit var presenter: InputEventPresenter
    private var eventName: String? = null
    private var description: String? = null
    private var actualSelectedDate: String? = null
    private var actualSelectedHour: Int? = null
    private var actualSelectedMinute: Int? = null
    private var actualSelectedIconHelper: Int? = null
    private var actualSelectedColorHelper: Int? = null
    private var actualTags: String? = null
    private var departDate: Date? = null
    private var getData: PersonalEvent? = PersonalEvent()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_event)
        presenter = InputEventPresenter(this)
        presenter.onCreate(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        initView()
    }

    override fun showEventData(data: PersonalEvent?) {
        etEvent.text = Editable.Factory.getInstance().newEditable(data?.event)
        etEventDescription.text = Editable.Factory.getInstance().newEditable(data?.eventDescription)
        etSelectedEventDate.text = Editable.Factory.getInstance().newEditable(dateFormat.format(data?.selectedDate))
    }

    override fun showTag(data: List<PersonalTags>?) {
        var position: Int? = null
        val dataForSelector: MutableList<String> = mutableListOf()
        Observable.fromIterable(data).doOnComplete {
            selector(resources.getString(R.string.pick_tag), dataForSelector) { dialogInterface, i ->
                Observable.fromArray(dataForSelector).subscribe { results ->
                    actualTags = results[i]
                    btnPickEventTag.text = results[i]
                    position = i
                }
                actualSelectedIconHelper = data?.get(position!!)?.newIcons
                actualSelectedColorHelper = data?.get(position!!)?.newColor
            }
        }.subscribe { results ->
            dataForSelector.add(results.newTags!!)
        }
    }

    override fun initView() {
        val i: Intent = intent
        if (i != null && i.hasExtra(INPUT_EVENT_ACTIVITY_KEY)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                showEventData(i.getParcelableExtra(INPUT_EVENT_ACTIVITY_KEY))
            }
        }
    }

    override fun initListener() {
        etSelectedEventDate.setOnClickListener { view -> pickRemindedDate(etSelectedEventDate) }
        swDepartReminderEvent.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hours, minutes ->
                    Observable.just(hours).subscribe { results -> actualSelectedHour = results }
                    Observable.just(minutes).subscribe { results -> actualSelectedMinute = results }
                    tvEventReminderViews.visibility = View.VISIBLE
                    tvEventReminderViews.text = resources.getString(R.string.reminded_info) + " ${hours} : ${minutes}"
                }, MainApplication.hours, MainApplication.minutes, true)
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, resources.getString(R.string.cancel)) { dialogInterface, i ->
                    swDepartReminder.isChecked = false
                    mTimePicker.dismiss()

                }
                mTimePicker.setTitle(resources.getString(R.string.select_time))
                mTimePicker.show()
            } else if (!b) {
                actualSelectedHour = null
                actualSelectedMinute = null
                tvEventReminderViews.visibility = View.GONE
                tvEventReminderViews.text = ""
            }
        }

        btnSaveEvents.setOnClickListener {
            if (etEvent?.text.isNullOrEmpty()) {
                etEvent.requestFocus()
                etEvent?.error = resources.getString(R.string.destination_cannot_be_empty)
            }
            if (etEventDescription?.text.isNullOrEmpty()) {
                etEventDescription.requestFocus()
                etEventDescription?.error = resources.getString(R.string.items_cannot_be_empty)
            }
            if (etSelectedEventDate?.text.isNullOrEmpty()) {
                etSelectedEventDate.requestFocus()
                etSelectedEventDate.error = resources.getString(R.string.date_cannot_be_empty)

            } else if (!etEvent?.text.isNullOrEmpty() && !etEventDescription?.text.isNullOrEmpty() && !etSelectedEventDate?.text.isNullOrEmpty()) {
                val dateExtract: String = etSelectedEventDate.text.toString().trim()
                description = etEventDescription.text.toString().trim()
                eventName = etEvent.text.toString().trim()
                departDate = dateFormat.parse(dateExtract)
                getData?.eventDescription = description
                getData?.event = eventName
                getData?.selectedDate = departDate
                getData?.selectedHour = actualSelectedHour
                getData?.selectedMinute = actualSelectedMinute
                getData?.tags = actualTags
                getData?.selectedIcon = actualSelectedIconHelper
                getData?.selectedColor = actualSelectedColorHelper
                insertData(getData)
            }

        }
        btnPickEventTag.setOnClickListener { presenter.getLiveDataAllTag(this) }
    }

    fun pickRemindedDate(onFocus: EditText) {
        KeyboardCloser.hideKeyboard(this)
        val datePicks = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, Year, MonthOfYear, Day ->
            onFocus.text = Editable.Factory.getInstance().newEditable(presenter.formatedDate(Year, MonthOfYear, Day))
            Observable.just(onFocus.text.toString().trim()).subscribe({ results ->
                actualSelectedDate
                etSelectedEventDate.error = null
            })
        }, MainApplication.years, MainApplication.month, MainApplication.days)
        datePicks.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicks.show()
    }

    fun insertData(data: PersonalEvent?) {
        presenter.saveEventData(data)
        finish()
        clearEditText()
    }

    fun clearEditText() {
        etEvent.text = Editable.Factory.getInstance().newEditable("")
        etEventDescription.text = Editable.Factory.getInstance().newEditable("")
        etSelectedEventDate.text = Editable.Factory.getInstance().newEditable("")
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            return true;
        }
        return false;

    }
    override fun onIconDialogIconsSelected(icons: Array<Icon>?) {
        selectedIcons = icons
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.finishObserving()
    }
}