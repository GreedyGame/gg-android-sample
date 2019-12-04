package com.greedygames.sample.sdk8

import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygame.android.core.campaign.CampaignStateListener
;
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.toast
const val TAG = "GG-SAMPLE"

open class BaseActivity : AppCompatActivity() {

    var mBaseCampaignStateListener = BaseCampaignListener()

    private var mRefreshTimer:CountDownTimer? = null

    
    fun initAds(){

        mGreedyGameAgent = GreedyGameAgent.Builder(this)
            .setGameId("66081223")
                //You can also use addUnitId(unitId:String)
            .addUnitList(listOf(
                "float-4343",
                "float-4344",
                "float-4345",
                "float-4346",
                "float-4347",
                "float-4348"))
            .enableAdmob(true)
            .withAgentListener(mBaseCampaignStateListener)
            .build()
        mGreedyGameAgent.init()
    }

    /**
     * BaseCampaignListener will be attached to GreedyGame Agent instance to wire events from SDK to appropriate classes.
     */
    open inner class BaseCampaignListener:CampaignStateListener{
        var receiver:CampaignStateListener?=null
        override fun onUnavailable() {
            startRefreshTimer()
            receiver?.onUnavailable()
            isGreedyGameAgentInitialised = true
        }

        override fun onAvailable(p0: String?) {
            "Available".toast(applicationContext)
            receiver?.onAvailable(p0)
            startRefreshTimer()
            isGreedyGameAgentInitialised = true
        }

        override fun onError(p0: String?) {
            receiver?.onError(p0)
            startRefreshTimer()
            p0?.toast(applicationContext)
            isGreedyGameAgentInitialised = true
        }

    }

    private fun stopRefreshTimer(){
        mRefreshTimer?.cancel()
        mRefreshTimer = null
    }

    private fun startRefreshTimer(){
        if(mRefreshTimer == null) {
            mRefreshTimer = object : CountDownTimer(62000, 1000) {
                override fun onFinish() {
                    Log.d(TAG, "Countdown timer complete. Refreshing and Reloading")
                    mGreedyGameAgent.startEventRefresh()
                    this.start()
                }

                override fun onTick(millisUntilFinished: Long) {

                }

            }.start()
        }
    }

    companion object {
        lateinit var mGreedyGameAgent: GreedyGameAgent
        var isGreedyGameAgentInitialised = false
    }
}