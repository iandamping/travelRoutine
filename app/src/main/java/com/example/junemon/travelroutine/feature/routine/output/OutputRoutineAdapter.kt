package com.example.junemon.travelroutine.feature.routine.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_routines.*

class OutputRoutineAdapter(val ctx: Context?, var listData: List<PersonalRoutines>, val listener: (PersonalRoutines) -> Unit)
    : RecyclerView.Adapter<OutputRoutineAdapter.ViewHolders>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        return ViewHolders(LayoutInflater.from(ctx).inflate(R.layout.item_routines, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.bindViews(listData.get(position), listener)
    }


    class ViewHolders(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(data: PersonalRoutines, listener: (PersonalRoutines) -> Unit) {
            tvRoutineDescription.text = data.description
            tvRoutines.text = data.routine
            if (data.selectedHour == 0) {
                llRoutineTimer.visibility = View.GONE
            } else {
                llRoutineTimer.visibility = View.VISIBLE
            }
            itemView.setOnClickListener { listener((data)) }
        }
    }
}