package com.example.junemon.travelroutine.feature.tags

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.repositories.Tags.TagsRepositories
import com.maltaisn.icondialog.IconHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tag.*
import java.math.BigInteger

class TagsAdapter(val ctx: Context?, var listData: List<PersonalTags>, val listener: (PersonalTags) -> Unit)
    : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_tag, parent, false), ctx)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewss(listData.get(position), listener)
    }

    class ViewHolder(override val containerView: View, var ctx: Context?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViewss(data: PersonalTags, listener: (PersonalTags) -> Unit) {
            tvTags.text = data.newTags
            if (data.newIcons != null) {
                var actualDataIcons: Int = data.newIcons!!
                var actualDataIconsLength: String = Integer.toString(actualDataIcons)
                val hexData = data.newColor?.let { it -> Integer.toHexString(it) }
                val actualColor = BigInteger(hexData, 16)
                val iconHelper: IconHelper = IconHelper.getInstance(ctx)
                if (actualDataIconsLength.length > 4) {
                    ivTagsOnly.setImageResource(data.newIcons!!)
                    ivTagsOnly.setBackgroundColor(data.newColor!!)
                } else if (actualDataIconsLength.length < 4) {
                    ivTagsOnly.setImageDrawable(iconHelper.getIcon(data.newIcons!!).getDrawable(ctx!!))
                    ivTagsOnly.setBackgroundColor(actualColor.toInt())
                }
            }
            ivDeleteTags.setOnClickListener {
                TagsRepositories.deleteTag(data, ctx, containerView)
            }
            itemView.setOnClickListener { listener((data)) }
        }
    }
}
