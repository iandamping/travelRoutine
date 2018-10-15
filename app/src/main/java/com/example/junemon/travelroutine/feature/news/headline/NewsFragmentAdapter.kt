package com.example.junemon.travelroutine.feature.news.headline

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.network.model.PersonalNews
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news_layout.*

class NewsFragmentAdapter(val ctx: Context?, var listData: List<PersonalNews.Article>, var listener: (PersonalNews.Article) -> Unit)
    : RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_news_layout, parent, false), ctx)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(listData.get(position), listener)
    }

    class ViewHolder(override var containerView: View, var ctx: Context?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindViews(data: PersonalNews.Article, listener: (PersonalNews.Article) -> Unit) {
            tvNewsAuthor.text = data.author
            tvNewsDate.text = data.publishedAt
            tvNewsTitle.text = data.title
            tvNewsDesc.text = data.description
            Picasso.get().load(data.urlToImage).placeholder(ContextCompat.getDrawable(ctx!!, R.drawable.ic_default_image)!!).into(ivNewsImage)
            itemView.setOnClickListener { listener((data)) }
        }
    }
}