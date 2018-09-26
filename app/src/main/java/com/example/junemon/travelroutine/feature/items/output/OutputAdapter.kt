package com.example.junemon.travelroutine.feature.items.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_output.*
import org.jetbrains.anko.imageResource

class OutputAdapter(val ctx: Context?, var listData: List<PersonalItems>, val listener: (PersonalItems) -> Unit)
    : RecyclerView.Adapter<OutputAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_output, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(listData.get(position), listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindViews(item: PersonalItems, listener: (PersonalItems) -> Unit) {
            val name = listOf("Travel", "Visiting Friends", "Road Trip", "Volunteer Travel", "Bussiness Travel", "Group Tour")
            tvDestination.text = item.destination
            tvItems.text = item.items
            if (item.selectedHour != null) {
                ivAlarmClock.visibility = View.VISIBLE
                tvDateReminder.visibility = View.VISIBLE
                tvTimer.visibility = View.VISIBLE
                tvDateReminder.text = dateFormat.format(item.selectedDate)
                tvTimer.text = "${item.selectedHour} : ${item.selectedMinute}"
            }
            if (item.tags == "Travel") {
                ivCircluar.imageResource = R.drawable.ic_bar
            }
            if (item.tags == "Visiting Friends") {
                ivCircluar.imageResource = R.drawable.ic_cityscape
            }
            if (item.tags == "Bussiness Travel") {
                ivCircluar.imageResource = R.drawable.ic_cofee_bean
            }
            if (item.tags == "Group Tour") {
                ivCircluar.imageResource = R.drawable.ic_foodstall
            }
            if (item.tags == "Road Trip") {
                ivCircluar.imageResource = R.drawable.ic_library
            }
            if (item.tags == "Volunteer Travel") {
                ivCircluar.imageResource = R.drawable.ic_vespa
            }



            itemView.setOnClickListener {
                listener((item))
            }
        }
    }

}