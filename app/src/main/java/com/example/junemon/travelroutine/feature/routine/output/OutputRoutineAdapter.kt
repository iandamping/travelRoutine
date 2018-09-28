package com.example.junemon.travelroutine.feature.routine.output

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainApplication.Companion.dateFormat
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_routines.*
import org.jetbrains.anko.imageResource

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
            if (data.selectedHour != null) {
                ivAlarmRoutineClock.visibility = View.VISIBLE
                tvDateRoutineReminder.visibility = View.VISIBLE
                tvRoutineTimer.visibility = View.VISIBLE
                tvDateRoutineReminder.text = dateFormat.format(data.selectedDate)
                tvRoutineTimer.text = "${data.selectedHour} : ${data.selectedMinute}"
            }
            if (data.tags == null) {
                ivRoutineCircluar.imageResource = R.drawable.ic_cofee_bean
            } else if (data.tags?.contains("Work")!!) let { ivRoutineCircluar.imageResource = R.drawable.ic_bar }
            else if (data.tags?.contains("Play Games")!!) let { ivRoutineCircluar.imageResource = R.drawable.ic_train }
            else if (data.tags?.contains("Fishing")!!) let { ivRoutineCircluar.imageResource = R.drawable.ic_library }
            else if (data.tags?.contains("Cooking")!!) let { ivRoutineCircluar.imageResource = R.drawable.ic_vespa }
            else if (data.tags?.contains("Swimming")!!) let { ivRoutineCircluar.imageResource = R.drawable.ic_foodstall }
            else if (data.tags?.contains("Boxing")!!) let { ivRoutineCircluar.imageResource = R.drawable.ic_cityscape }
            itemView.setOnClickListener { listener((data)) }
        }
    }
}