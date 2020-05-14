package com.greedygame.sample.sdk

import android.content.Context
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener

object GreedyGameAdManager {
    val isInitialized:Boolean
        get()  = GreedyGameAds.isSdkInitialized

    fun init(context: Context, listener: GreedyGameAdsEventsListener? = null) {
        if (isInitialized) {
            return
        }
        val appConfig = AppConfig.Builder(context)
            .withAppId("75512866")
            .build()
        GreedyGameAds.initWith(appConfig, listener)
    }
}