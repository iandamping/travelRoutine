package com.example.junemon.travelroutine.feature.items.input

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
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.MainApplication.Companion.days
import com.example.junemon.travelroutine.MainApplication.Companion.hours
import com.example.junemon.travelroutine.MainApplication.Companion.minutes
import com.example.junemon.travelroutine.MainApplication.Companion.month
import com.example.junemon.travelroutine.MainApplication.Companion.years
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.helper.KeyboardCloser
import com.example.junemon.travelroutine.helper.alarms.AlarmSetter
import com.maltaisn.icondialog.Icon
import com.maltaisn.icondialog.IconDialog
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_input_items.*
import org.jetbrains.anko.selector
import java.util.*


class InputActivity : AppCompatActivity(), InputView, IconDialog.Callback {


    companion object {
        val INPUT_ITEMS_ACTIVITY_KEY: String = "this is key"
    }

    private var selectedIcons: Array<Icon>? = null
    private var iconDialog = IconDialog()
    private val DEFAULT_TASK_ID = -1
    private var mTaskId = DEFAULT_TASK_ID
    private lateinit var presenter: InputPresenter
    private var itemName: String? = null
    private var destinantion: String? = null
    private var actualSelectedDate: String? = null
    private var actualSelectedHour: Int? = null
    private var actualSelectedMinute: Int? = null
    private var actualSelectedIconHelper: Int? = null
    private var actualSelectedColorHelper: Int? = null
    private var actualTags: String? = null
    private var departDate: Date? = null
    private var getData: PersonalItems? = PersonalItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_items)
        presenter = InputPresenter(this)
        presenter.onCreate(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        initView()
    }


    override fun showData(data: PersonalItems?) {
        etBarang.text = Editable.Factory.getInstance().newEditable(data?.items)
        etDestination.text = Editable.Factory.getInstance().newEditable(data?.destination)
        etDepartDates.text = Editable.Factory.getInstance().newEditable(dateFormat.format(data?.selectedDate))
    }

    override fun initView() {
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
                    tvDepartDateslReminderViews.visibility = View.VISIBLE
                    tvDepartDateslReminderViews.text = resources.getString(R.string.reminded_info) + " ${hours} : ${minutes}"
                }, hours, minutes, true)
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, resources.getString(R.string.cancel)) { dialogInterface, i ->
                    swDepartReminder.isChecked = false
                    mTimePicker.dismiss()

                }
                mTimePicker.setTitle(resources.getString(R.string.select_time))
                mTimePicker.show()
            } else if (!b) {
                actualSelectedHour = null
                actualSelectedMinute = null
                tvDepartDateslReminderViews.visibility = View.GONE
                tvDepartDateslReminderViews.text = ""
            }
        }

        btnSave.setOnClickListener {
            if (etDestination?.text.isNullOrEmpty()) {
                etDestination.requestFocus()
                etDestination?.error = resources.getString(R.string.destination_cannot_be_empty)
            }
            if (etBarang?.text.isNullOrEmpty()) {
                etBarang.requestFocus()
                etBarang?.error = resources.getString(R.string.items_cannot_be_empty)
            }
            if (etDepartDates?.text.isNullOrEmpty()) {
                etDepartDates.requestFocus()
                etDepartDates.error = resources.getString(R.string.date_cannot_be_empty)

            } else if (!etDestination?.text.isNullOrEmpty() && !etBarang?.text.isNullOrEmpty() && !etDepartDates?.text.isNullOrEmpty()) {
                val dateExtract: String = etDepartDates.text.toString().trim()
                destinantion = etDestination.text.toString().trim()
                itemName = etBarang.text.toString().trim()
                departDate = dateFormat.parse(dateExtract)
                getData?.destination = destinantion
                getData?.items = itemName
                getData?.selectedDate = departDate
                getData?.selectedHour = actualSelectedHour
                getData?.selectedMinute = actualSelectedMinute
                getData?.tags = actualTags
                getData?.selectedIcon = actualSelectedIconHelper
                getData?.selectedColor = actualSelectedColorHelper
                insertData(getData)
            }

        }
        btnPickTag.setOnClickListener {
            presenter.getLiveDataAllTag(this)
        }
        ivPicksIcon.setOnClickListener {
            iconDialog.setSelectedIcons(0)
            iconDialog.show(getSupportFragmentManager(), "test")
        }


    }

    fun pickRemindedDate(onFocus: EditText) {
        KeyboardCloser.hideKeyboard(this)
        val datePicks = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, Year, MonthOfYear, Day ->
            onFocus.text = Editable.Factory.getInstance().newEditable(presenter.formatedDate(Year, MonthOfYear, Day))
            Observable.just(onFocus.text.toString().trim()).subscribe({ results ->
                actualSelectedDate
                etDepartDates.error = null
            })
        }, years, month, days)
        datePicks.datePicker.minDate = System.currentTimeMillis() - 1000
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


    override fun showTag(data: List<PersonalTags>?) {
        val dataForSelector: MutableList<String> = mutableListOf()
        Observable.fromIterable(data).subscribe { results ->
            dataForSelector.add(results.newTags!!)
            actualSelectedIconHelper = results.newIcons
            actualSelectedColorHelper = results.newColor
        }
        selector(resources.getString(R.string.pick_tag), dataForSelector) { dialogInterface, i ->
            actualTags = dataForSelector[i]
            btnPickTag.text = dataForSelector[i]

        }
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
        ivPicksIcon.setImageDrawable(selectedIcons?.get(0)?.getDrawable(this))
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}