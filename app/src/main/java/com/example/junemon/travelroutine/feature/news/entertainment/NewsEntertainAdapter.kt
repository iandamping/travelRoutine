package com.example.junemon.travelroutine.feature.news.entertainment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news_entertainment_layout.*

class NewsEntertainAdapter(val ctx: Context?, var listData: List<PersonalNewsEntertainment.Article>, var listener: (PersonalNewsEntertainment.Article) -> Unit)
    : RecyclerView.Adapter<NewsEntertainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_news_entertainment_layout, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(listData.get(position), listener)
    }

    class ViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(data: PersonalNewsEntertainment.Article, listener: (PersonalNewsEntertainment.Article) -> Unit) {
            tvEntertainmentAuthor.text = data.author
            tvEntertainmentDate.text = data.publishedAt
            tvEntertainmentTitle.text = data.title
            tvEntertainmentDesc.text = data.description
            Picasso.get().load(data.urlToImage).into(ivEntertainmentImage)
            itemView.setOnClickListener { listener((data)) }
        }
    }
}