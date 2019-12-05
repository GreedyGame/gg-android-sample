package com.greedygame.sample.sdk8.showcasemenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygame.sample.sdk8.BaseActivity
import com.greedygame.sample.sdk8.R
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.TravelDashboard
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager.ShowcaseViewPagerAdapter
import com.greedygame.sample.sdk8.utils.notimportant.SizeReductionPageTransformer
import kotlinx.android.synthetic.main.activity_showcase_menu.*

class ShowcaseMenu : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_menu)
        initViewPager()
        setClickListeners()
        //Registering the event receiver for this class to the BaseClass
        mBaseCampaignStateListener.receiver = ShowcaseListener()
        if(isGreedyGameAgentInitialised) {
           hideLoader()
        }
        else {
            showLoader()
            initAds()
        }
    }


    private fun setClickListeners(){
        seeDemoButton.setOnClickListener {
            startActivity(Intent(this@ShowcaseMenu, TravelDashboard::class.java))
            finish()
        }

        knowMoreButton.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW).apply {
                setData(Uri.parse("https://www.greedygame.com"))
            }
            startActivity(webIntent)
        }
    }
    private fun showLoader(){
        buttonBar.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }
    private fun hideLoader(){
        buttonBar.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }
    private fun initViewPager(){
        with(contentViewPager){
            adapter = ShowcaseViewPagerAdapter()
            setPageTransformer(SizeReductionPageTransformer())
        }
    }


    inner class ShowcaseListener:CampaignStateListener{
        override fun onUnavailable() {
            loader.visibility = View.INVISIBLE
            hideLoader()
        }

        override fun onAvailable(p0: String?) {
            loader.visibility  = View.GONE
            hideLoader()

        }

        override fun onError(p0: String?) {
            loader.visibility  = View.GONE

        }

    }
}
