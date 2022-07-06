package com.dspot.dspotandroid.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*

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

        fun handleSystemBar(window: Window, act: Activity) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

            setWindowFlag(
                act,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )

            window.statusBarColor = Color.TRANSPARENT
            setSystemBarLight(act)
        }

        fun formatDate(date: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val consultationDate = sdf.parse(date)
            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            return formatter.format(consultationDate!!)
        }
    }
}