package com.example.junemon.travelroutine.feature.event.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainApplication
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.maltaisn.icondialog.IconHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event_output.*
import org.jetbrains.anko.imageResource
import java.math.BigInteger

class OutputEventAdapter(val ctx: Context?, var listData: List<PersonalEvent>, val listener: (PersonalEvent) -> Unit)
    : RecyclerView.Adapter<OutputEventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_event_output, p0, false), ctx)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(p0: EventViewHolder, p1: Int) {
        p0.bindViews(listData.get(p1), listener)
    }


    class EventViewHolder(override var containerView: View, var ctx: Context?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(item: PersonalEvent, listener: (PersonalEvent) -> Unit) {
            tvOutputEvent.text = item.event
            tvOutputEventDescription.text = item.eventDescription
            if (item.selectedHour != null) {
                ivAlarmEventClock.visibility = View.VISIBLE
                tvDateEventReminder.text = MainApplication.dateFormat.format(item.selectedDate)
                tvEventTimer.text = "${item.selectedHour} : ${item.selectedMinute}"
            }
            if (item.tags == null) {
                ivEventIcon.imageResource = R.drawable.ic_cofee_bean
            } else if (!item.tags.isNullOrEmpty() && item.selectedIcon != null) {
                var actualDataIcons: Int = item.selectedIcon!!
                var actualDataIconsLength: String = Integer.toString(actualDataIcons)
                val hexData = item.selectedColor?.let { Integer.toHexString(it) }
                val actualColor = hexData?.let { it -> BigInteger(it, 16) }
                val iconHelper: IconHelper = IconHelper.getInstance(ctx)
                if (actualDataIconsLength.length > 4) {
                    ivEventIcon.setImageResource(item.selectedIcon!!)
                    ivEventIcon.setBackgroundColor(item.selectedColor!!)
                } else if (actualDataIconsLength.length < 4) {
                    ivEventIcon.setImageDrawable(iconHelper.getIcon(item.selectedIcon!!).getDrawable(ctx!!))
                    if (actualColor != null) {
                        ivEventIcon.setBackgroundColor(actualColor.toInt())
                    }
                }

            }
            itemView.setOnClickListener {
                listener((item))
            }
        }

    }
}