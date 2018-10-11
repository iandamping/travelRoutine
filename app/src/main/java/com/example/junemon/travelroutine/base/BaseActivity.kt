package com.example.junemon.travelroutine.base

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup


open class BaseActivity : AppCompatActivity() {

    fun getRootView(): View? {
        val contentViewGroup = findViewById<View>(android.R.id.content) as ViewGroup
        var rootView: View? = null

        if (contentViewGroup != null)
            rootView = contentViewGroup.getChildAt(0)

        if (rootView == null)
            rootView = window.decorView.rootView

        return rootView
    }
}