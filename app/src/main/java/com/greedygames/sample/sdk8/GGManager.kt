package com.greedygames.sample.sdk8

import android.app.Activity
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygame.android.core.campaign.CampaignStateListener

object GGManager {
    val stateListener  = CampaignListener()
    private lateinit var mGreedyGameAgent:GreedyGameAgent

    fun buildGreedyGameAgent(activity:Activity){
        mGreedyGameAgent = GreedyGameAgent.Builder(activity)
            .setGameId("25366126")
            .addUnitList(listOf("float-4196","unit-4296"))
            .enableAdmob(true)
            .withAgentListener(stateListener)
            .build()
    }

    class CampaignListener:CampaignStateListener{
        override fun onUnavailable() {

        }

        override fun onAvailable(p0: String?) {

        }

        override fun onError(p0: String?) {

        }

    }
}