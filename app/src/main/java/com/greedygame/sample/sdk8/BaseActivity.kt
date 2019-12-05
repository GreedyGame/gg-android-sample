package com.greedygame.sample.sdk8

import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygame.sample.sdk8.utils.toast

const val TAG = "GG-SAMPLE"

open class BaseActivity : AppCompatActivity() {

    fun initAds(){

        mGreedyGameAgent = GreedyGameAgent.Builder(this)
            .setGameId("66081223")
            //You can also use addUnitId(unitId:String)
            .addUnitList(listOf(
                "float-4343",
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

    companion object {

        private var mRefreshTimer:CountDownTimer? = null
        lateinit var mGreedyGameAgent: GreedyGameAgent
        val mBaseCampaignStateListener = BaseCampaignListener()
        var isGreedyGameAgentInitialised = false

        private fun startRefreshTimer(){
            if(mRefreshTimer == null) {
                mRefreshTimer = object : CountDownTimer(70000, 1000) {
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


        open class BaseCampaignListener:CampaignStateListener{
            var receiver:CampaignStateListener?=null
            override fun onUnavailable() {
                startRefreshTimer()
                receiver?.onUnavailable()
                isGreedyGameAgentInitialised = true
            }

            override fun onAvailable(p0: String?) {
                receiver?.onAvailable(p0)
                startRefreshTimer()
                isGreedyGameAgentInitialised = true
            }

            override fun onError(p0: String?) {
                receiver?.onError(p0)
                startRefreshTimer()
                BaseApplication.appContext?.let {
                    p0?.toast(it)
                }

                isGreedyGameAgentInitialised = true
            }

        }
    }
}