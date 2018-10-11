package com.example.junemon.travelroutine.feature.items.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.maltaisn.icondialog.IconHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_output.*
import org.jetbrains.anko.imageResource
import java.math.BigInteger

class OutputAdapter(val ctx: Context?, var listData: List<PersonalItems>, val listener: (PersonalItems) -> Unit)
    : RecyclerView.Adapter<OutputAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_output, parent, false), ctx)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(listData.get(position), listener)
    }

    class ViewHolder(override val containerView: View, var ctx: Context?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(item: PersonalItems, listener: (PersonalItems) -> Unit) {
            var actualDataIcons: Int = item.selectedIcon!!
            var actualDataIconsLength: String = Integer.toString(actualDataIcons)
            val hexData = item.selectedColor?.let { Integer.toHexString(it) }
            val actualColor = hexData?.let { it -> BigInteger(it, 16) }

            tvDestination.text = item.destination
            tvItems.text = item.items
            if (item.selectedHour != null) {
                ivAlarmClock.visibility = View.VISIBLE
                tvDateReminder.text = dateFormat.format(item.selectedDate)
                tvTimer.text = "${item.selectedHour} : ${item.selectedMinute}"
            }
            if (item.tags == null) {
                ivCircluar.imageResource = R.drawable.ic_cofee_bean
            } else if (!item.tags.isNullOrEmpty() && item.selectedIcon != null) {
                val iconHelper: IconHelper = IconHelper.getInstance(ctx)
                if (actualDataIconsLength.length > 4) {
                    ivCircluar.setImageResource(item.selectedIcon!!)
                    ivCircluar.setBackgroundColor(item.selectedColor!!)
                } else if (actualDataIconsLength.length < 4) {
                    ivCircluar.setImageDrawable(iconHelper.getIcon(item.selectedIcon!!).getDrawable(ctx!!))
                    if (actualColor != null) {
                        ivCircluar.setBackgroundColor(actualColor.toInt())
                    }
                }

            }
            itemView.setOnClickListener {
                listener((item))
            }
        }
    }

}
