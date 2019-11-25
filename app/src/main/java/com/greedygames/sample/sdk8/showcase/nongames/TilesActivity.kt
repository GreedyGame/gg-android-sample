package com.greedygames.sample.sdk8.showcase.nongames

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.adapters.TilesListAdapter
import kotlinx.android.synthetic.main.activity_tiles.*

class TilesActivity : BaseActivity() {
    val campaignListener  = TilesCampaignListener()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiles)
    }

    override fun onStart() {
        super.onStart()
        mBaseCampaignStateListener.receiver = campaignListener
        if(mGreedyGameAgent.isCampaignAvailable)
            setList()
    }

    private fun setList(){
        tilesList.layoutManager = GridLayoutManager(this,2)
        tilesList.adapter = TilesListAdapter(mGreedyGameAgent,this,this);
    }

    inner class TilesCampaignListener:CampaignStateListener{
        override fun onUnavailable() {
            
        }

        override fun onAvailable(p0: String?) {
            setList()
        }

        override fun onError(p0: String?) {
            
        }
    }
}
