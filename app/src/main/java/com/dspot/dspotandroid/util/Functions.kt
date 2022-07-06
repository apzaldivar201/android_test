package com.dspot.dspotandroid.util

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.view.View

sealed class Functions {
    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }

        fun setSystemBarLight(act: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val view = act.findViewById<View>(android.R.id.content)
                var flags = view.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                view.systemUiVisibility = flags
            }
        }

        private fun toDp(px: Int): Int = (px / Resources.getSystem().displayMetrics.density).toInt()

        private fun toPx(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}