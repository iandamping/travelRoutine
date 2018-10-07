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

    private var revealX: Int = 0
    private var revealY: Int = 0

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            mView.visibility = View.INVISIBLE

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)
            val viewTreeObserver = mView.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        mView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            mView.visibility = View.VISIBLE
        }
    }

    fun revealActivity(x: Int, y: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val finalRadius = Math.max(mView.width, mView.height) * 1.1F

            val circularReveal = ViewAnimationUtils.createCircularReveal(mView, x, y, 0f, finalRadius)
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
            val finalRadius = Math.max(mView.width, mView.height) * 1.1F
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                    mView, revealX, revealY, finalRadius, 0f)

            circularReveal.duration = 500
            circularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mView.visibility = View.INVISIBLE
                    mActivity.finish()
                    mActivity.overridePendingTransition(0, 0)
                }
            })
            circularReveal.start()
        }
    }

    companion object {
        const val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"
    }
}