package com.example.junemon.travelroutine.helper

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


class KeyboardCloser {
    companion object {
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = object : View(activity) {}
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}