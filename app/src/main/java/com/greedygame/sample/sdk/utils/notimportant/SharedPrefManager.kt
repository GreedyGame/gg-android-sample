package com.greedygame.sample.sdk.utils.notimportant

import android.content.Context
import com.greedygame.sample.sdk.BaseApplication

object SharedPrefManager {
    private val sharePref = BaseApplication.appContext?.getSharedPreferences("GG_DEMO_SHARED_PREFRENCE",Context.MODE_PRIVATE)
    private val editor = sharePref?.edit()
    private const val VALUE_KEY = "COACHMARKS";

    var shouldShowCoachmarks:Boolean = false
        get() =
            sharePref?.getBoolean(VALUE_KEY,true)?:false
        set(value) {
            field = value
            editor?.putBoolean(VALUE_KEY, value)
            editor?.commit()
        }
}