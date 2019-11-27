package com.greedygames.sample.sdk8

import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygames.sample.sdk8.showcase.nongames.toast

open class BaseActivity : AppCompatActivity() {

    var mBaseCampaignStateListener = BaseCampaginListener()
    
    fun initAds(){
        mGreedyGameAgent = GreedyGameAgent.Builder(this)
            .setGameId("66081223")
            .addUnitList(listOf(
                "float-4343",
                "float-4344",
                "float-4345"))
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
        }

        override fun onError(p0: String?) {
            receiver?.onError(p0)
        }

    }

    companion object {
        lateinit var mGreedyGameAgent: GreedyGameAgent;
    }
}