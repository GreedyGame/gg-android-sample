package com.greedygame.sample.sdk8

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener

class BaseApplication : MultiDexApplication(){
    companion object{
        var appContext: Context? = null
        fun initAds(listener: GreedyGameAdsEventsListener){
            appContext?.let {
                val appConfig = AppConfig.Builder(it)
                    .withAppId("1234")
                    .build()
                GreedyGameAds.initWith(appConfig,listener)
            }

        }
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}