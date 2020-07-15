package com.greedygame.sample.sdk

import android.content.Context
import androidx.multidex.MultiDexApplication

class BaseApplication : MultiDexApplication(){
    companion object{
        var appContext: Context? = null
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}