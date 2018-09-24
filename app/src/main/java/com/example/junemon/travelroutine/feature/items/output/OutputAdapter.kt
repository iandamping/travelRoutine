package com.example.junemon.travelroutine.feature.items.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalItems
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_output.*

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
            tvDestination.text = item.destination
            tvItems.text = item.items
            if (item.selectedHour == 0) {
                llTimer.visibility = View.GONE
            } else llTimer.visibility = View.VISIBLE
            itemView.setOnClickListener {
                listener((item))
            }
        }
    }

}