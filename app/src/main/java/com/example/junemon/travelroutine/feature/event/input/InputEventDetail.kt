package com.example.junemon.travelroutine.feature.event.input

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.example.junemon.travelroutine.database.model.PersonalTags
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_output_event_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.text.DateFormat

class InputEventDetail : AppCompatActivity(), InputEventView {
    private var data: PersonalEvent? = null
    lateinit var presenter: InputEventPresenter
    private var menuItem: Menu? = null
    private val df: DateFormat = MainApplication.dateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NightAppTheme)
        } else setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_output_event_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = InputEventPresenter(this)
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

    override fun showEventData(data: PersonalEvent?) {
//        if (data?.selectedIcon != null){
//            var actualDataIcons: Int = data.selectedIcon!!
//            var actualDataIconsLength: String = Integer.toString(actualDataIcons)
//            val hexData = data.selectedColor?.let { Integer.toHexString(it) }
//            val actualColor = hexData?.let { it -> BigInteger(it, 16) }
//            val iconHelper: IconHelper = IconHelper.getInstance(ctx)
//            if (actualDataIconsLength.length > 4) {
//                tvEventIcon.setImageResource(data.selectedIcon!!)
//                tvEventIcon.setBackgroundColor(data.selectedColor!!)
//            } else if (actualDataIconsLength.length < 4) {
//                tvEventIcon.setImageDrawable(iconHelper.getIcon(data.selectedIcon!!).getDrawable(ctx))
//                if (actualColor != null) {
//                    tvEventIcon.setBackgroundColor(actualColor.toInt())
//                }
//            }
//        }
        tvSelectedEvent.text = data?.event
        tvEventDescription.text = data?.eventDescription
        tvEventDate.text = df.format(data?.selectedDate)
        if (data?.selectedHour != null) {
            llAlarmEvent.visibility = View.VISIBLE
            tvAlarmEventHours.text = "${data?.selectedHour} : ${data.selectedMinute}"
        }
    }

    override fun showTag(data: List<PersonalTags>?) {
    }

    override fun initView() {
        val i: Intent = intent
        if (i != null && i.hasExtra(InputEventActivity.INPUT_EVENT_ACTIVITY_KEY)) {
            data = i.getParcelableExtra(InputEventActivity.INPUT_EVENT_ACTIVITY_KEY)
            presenter.getDataDetail(data)
        }
    }

    override fun initListener() {
    }

    protected fun onClickHome() {
        super.onBackPressed()
    }

}