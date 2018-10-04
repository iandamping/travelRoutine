package com.example.junemon.travelroutine.helper.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator


class RevealAnimation(private val mView: View, intent: Intent, private val mActivity: Activity) {

    private var revealX: Int = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
    private var revealY: Int = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            mView.setVisibility(View.INVISIBLE)


            val viewTreeObserver = mView.getViewTreeObserver()
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        mView.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            mView.setVisibility(View.VISIBLE)
        }
    }

    fun revealActivity(x: Int, y: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val finalRadius = Math.max(mView.getWidth(), mView.getHeight())
//            (Math.max(mView.getWidth(), mView.getHeight()) * 1.1F)

            val circularReveal = ViewAnimationUtils.createCircularReveal(mView, x, y, 0f, finalRadius.toFloat())
            circularReveal.duration = 500
            circularReveal.interpolator = AccelerateInterpolator()
            mView.setVisibility(View.VISIBLE)
            circularReveal.start()
        } else {
            mActivity.finish()
        }
    }

    fun unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mActivity.finish()
        } else {
            val finalRadius = (Math.max(mView.getWidth(), mView.getHeight()))
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                    mView, revealX, revealY, finalRadius.toFloat(), 0f)

            circularReveal.duration = 800
            circularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mView.setVisibility(View.INVISIBLE)
                    mActivity.finish()
                    mActivity.overridePendingTransition(0, 0)
                }
            })
            circularReveal.start()
        }
    }

    companion object {
        val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"
    }
}