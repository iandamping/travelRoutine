package com.example.junemon.travelroutine.feature.news.bussines

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news_bussines_layout.*

class NewBussinesAdapter(val ctx: Context?, var listData: List<PersonalNewsBussines.Article>, val listener: (PersonalNewsBussines.Article) -> Unit)
    : RecyclerView.Adapter<NewBussinesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_news_bussines_layout, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listData.get(position), listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindData(data: PersonalNewsBussines.Article, listener: (PersonalNewsBussines.Article) -> Unit) {
            tvBussinesAuthor.text = data.author
            tvBussinesDate.text = data.publishedAt
            tvBussinesTitle.text = data.title
            tvBussinesDesc.text = data.description
            Picasso.get().load(data.urlToImage).into(ivBussinesImage)
            itemView.setOnClickListener { listener((data)) }
        }
    }
}