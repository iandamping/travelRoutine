package com.example.junemon.travelroutine.feature.splash

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.junemon.travelroutine.MainActivity
import com.example.junemon.travelroutine.R
import com.example.junemon.travelroutine.helper.animations.RevealAnimation
import kotlinx.android.synthetic.main.activity_main_pager_splash.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onPageChangeListener


class SplashActivity : AppCompatActivity() {
    private var currentPage: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pager_splash)
        if (vpMainPageSplashActivity != null) {
            vpMainPageSplashActivity.adapter = buildAdapter()
        }
        tabDots.setupWithViewPager(vpMainPageSplashActivity, true)
        buttonShowUp()
    }

    fun buildAdapter(): PagerAdapter {
        return SplashSimpleAdapter(this)
    }

    fun buttonShowUp() {
        vpMainPageSplashActivity.onPageChangeListener {
            onPageSelected { results ->
                currentPage

                if (results.equals(2)) {
                    imBtnSplash.animate().alpha(1f)
                    imBtnSplash.visibility = ViewGroup.VISIBLE
                } else if (results < 2) {
                    imBtnSplash.animate().alpha(0f)
                }
            }
        }
        imBtnSplash.setOnClickListener { view ->
            startRevealActivity(view)

        }
    }

    private fun startRevealActivity(v: View) {
        val revealX = v.right
        val revealY = v.bottom
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX)
        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY)
        ActivityCompat.startActivity(this, intent, null)
        overridePendingTransition(0, 0);
        finish()
    }

}