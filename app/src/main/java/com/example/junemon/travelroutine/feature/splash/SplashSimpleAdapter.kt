package com.example.junemon.travelroutine.feature.splash

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.R.id.imBtnSplash
import org.jetbrains.anko.imageResource

class SplashSimpleAdapter(var ctx: Context) : PagerAdapter() {

    private val slideImages: Array<Int> =
            arrayOf(R.drawable.ic_goal_with_arrow, R.drawable.ic_notebook, R.drawable.ic_splash_logo)

    private val slideHeader: Array<String> = arrayOf(ctx.getString(R.string.splash_header_1)
            , ctx.getString(R.string.splash_header_2)
            , ctx.getString(R.string.splash_header_3))

    private val slideDesc: Array<String> = arrayOf(ctx.getString(R.string.splash_description_1)
            , ctx.getString(R.string.splash_description_2)
            , ctx.getString(R.string.splash_description_3))


    override fun getCount(): Int {
        return slideImages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val views: View = LayoutInflater.from(ctx).inflate(R.layout.item_splash_first, container, false)
        val ivSplashClocks: ImageView = views.findViewById(R.id.ivSplashClocks) as ImageView
        val tvSplashHeader: TextView = views.findViewById(R.id.tvSplashHeader) as TextView
        val tvSplashDesc: TextView = views.findViewById(R.id.tvSplashDesc) as TextView
        ivSplashClocks.imageResource = slideImages.get(position)
        tvSplashHeader.text = slideHeader.get(position)
        tvSplashDesc.text = slideDesc.get(position)
        container.addView(views)

        return views
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}