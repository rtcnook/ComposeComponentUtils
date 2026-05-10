package com.example.widgetutilslib.utils

import android.content.res.Resources

/**
 * @author qlw
 **/
object UnitUtils {

    fun wpx(resources: Resources): Int {
        return resources.displayMetrics.widthPixels
    }

    fun hpx(resources: Resources): Int {
        return resources.displayMetrics.heightPixels
    }

    fun dp2px(resources: Resources, dp: Float): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }

    fun px2dp(resources: Resources, px: Float): Int {
        return (px / resources.displayMetrics.density + 0.5f).toInt()
    }
}
