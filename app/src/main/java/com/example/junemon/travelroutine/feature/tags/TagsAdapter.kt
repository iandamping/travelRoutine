package com.example.junemon.travelroutine.feature.tags

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.repositories.Tags.TagsRepositories
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tag.*

class TagsAdapter(var ctx: Context?, var listData: List<PersonalTags>, val listener: (PersonalTags) -> Unit)
    : RecyclerView.Adapter<TagsAdapter.ViewHolders>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolders {
        return ViewHolders(LayoutInflater.from(ctx).inflate(R.layout.item_tag, p0, false), ctx)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(p0: ViewHolders, p1: Int) {
        p0.bindViews(listData.get(p1), listener)
    }

    class ViewHolders(override var containerView: View, var ctx: Context?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(data: PersonalTags, listener: (PersonalTags) -> Unit) {
            tvTags.text = data.newTags
            ivDeleteTags.setOnClickListener {
                TagsRepositories.deleteTag(data, ctx, containerView)
            }
            itemView.setOnClickListener { listener((data)) }
        }
    }
}