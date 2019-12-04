package com.greedygames.sample.sdk8.showcasemenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.TravelDashboard
import kotlinx.android.synthetic.main.activity_showcase_menu.*

class ShowcaseMenu : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_menu)
        initAds()
        //Registering the event receiver for this class to the BaseClass
        mBaseCampaignStateListener.receiver = ShowcaseListener()
    }



    inner class ShowcaseListener:CampaignStateListener{
        override fun onUnavailable() {
            loader.visibility = View.INVISIBLE
            startActivity(Intent(this@ShowcaseMenu,TravelDashboard::class.java))
        }

        override fun onAvailable(p0: String?) {
            loader.visibility  = View.GONE
            startActivity(Intent(this@ShowcaseMenu,TravelDashboard::class.java))

        }

        override fun onError(p0: String?) {

        }

    }
}
