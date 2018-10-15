package com.example.junemon.travelroutine.feature.routine.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.maltaisn.icondialog.IconHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_routines.*
import org.jetbrains.anko.imageResource
import java.math.BigInteger

class OutputRoutineAdapter(val ctx: Context?, var listData: List<PersonalRoutines>, val listener: (PersonalRoutines) -> Unit)
    : RecyclerView.Adapter<OutputRoutineAdapter.ViewHolders>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        return ViewHolders(LayoutInflater.from(ctx).inflate(R.layout.item_routines, parent, false), ctx)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.bindViews(listData.get(position), listener)
    }


    class ViewHolders(override val containerView: View, var ctx: Context?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(data: PersonalRoutines, listener: (PersonalRoutines) -> Unit) {
            tvRoutineDescription.text = data.description
            tvRoutines.text = data.routine
            if (data.selectedHour != null) {
                ivAlarmRoutineClock.visibility = View.VISIBLE
                tvDateRoutineReminder.visibility = View.VISIBLE
                tvRoutineTimer.visibility = View.VISIBLE
                ivAlarmRoutineClock.visibility = View.VISIBLE
                tvDateRoutineReminder.text = dateFormat.format(data.selectedDate)
                tvRoutineTimer.text = "${data.selectedHour} : ${data.selectedMinute}"
            }
            if (data.tags == null) {
                ivRoutineCircluar.imageResource = R.drawable.ic_cofee_bean
            } else if (!data.tags.isNullOrEmpty() && data.selectedIcon != null) {
                var actualDataIcons: Int = data.selectedIcon!!
                var actualDataIconsLength: String = Integer.toString(actualDataIcons)
                val hexData = data.selectedColor?.let { it -> Integer.toHexString(it) }
                val actualColor = BigInteger(hexData, 16)
                val iconHelper: IconHelper = IconHelper.getInstance(ctx)
                if (actualDataIconsLength.length > 4) {
                    ivRoutineCircluar.setImageResource(data.selectedIcon!!)
                    ivRoutineCircluar.setBackgroundColor(data.selectedColor!!)
                } else if (actualDataIconsLength.length < 4) {
                    ivRoutineCircluar.setImageDrawable(iconHelper.getIcon(data.selectedIcon!!).getDrawable(ctx!!))
                    if (actualColor != null) {
                        ivRoutineCircluar.setBackgroundColor(actualColor.toInt())
                    }
                }

            }
            itemView.setOnClickListener { listener((data)) }
        }
    }
}