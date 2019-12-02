package com.greedygames.sample.sdk8

import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygames.sample.sdk8.showcase.nongames.toast

open class BaseActivity : AppCompatActivity() {

    var mBaseCampaignStateListener = BaseCampaginListener()
    private var mRefreshTimer:CountDownTimer? = null
    
    fun initAds(){
        mGreedyGameAgent = GreedyGameAgent.Builder(this)
            .setGameId("66081223")
            .addUnitList(listOf(
                "float-4343",
                "float-4344",
                "float-4345",
                "float-4346"))
            .enableAdmob(true)
            .withAgentListener(mBaseCampaignStateListener)
            .build()
        mGreedyGameAgent.init()
    }

    fun refreshAds(){
        object :CountDownTimer(62000,1000){
            override fun onFinish() {
                this.start()
                mGreedyGameAgent.startEventRefresh()
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()
    }


    override fun onStart() {
        super.onStart()

    }

    open inner class BaseCampaginListener:CampaignStateListener{
        var receiver:CampaignStateListener?=null
        override fun onUnavailable() {
            receiver?.onUnavailable()
        }

        override fun onAvailable(p0: String?) {
            "Available".toast(applicationContext)
            receiver?.onAvailable(p0)
            startRefreshTimer()
        }

        override fun onError(p0: String?) {
            receiver?.onError(p0)
        }

    }
    private fun startRefreshTimer(){
        if(mRefreshTimer == null) {
            mRefreshTimer = object : CountDownTimer(62000, 1000) {
                override fun onFinish() {
                    Log.d("JUDE", "Countdown timer complete. Refreshing and Reloading")
                    mGreedyGameAgent.startEventRefresh()
                    this.start()
                }

                override fun onTick(millisUntilFinished: Long) {

                }

            }.start()
        }
    }

    companion object {
        lateinit var mGreedyGameAgent: GreedyGameAgent;
    }
}