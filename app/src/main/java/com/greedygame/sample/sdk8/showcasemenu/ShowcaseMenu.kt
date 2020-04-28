package com.greedygame.sample.sdk8.showcasemenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener
import com.greedygame.core.models.InitErrors
import com.greedygame.sample.sdk8.BaseActivity
import com.greedygame.sample.sdk8.BaseApplication
import com.greedygame.sample.sdk8.R
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.TravelDashboard
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager.ShowcaseViewPagerAdapter
import com.greedygame.sample.sdk8.utils.notimportant.SizeReductionPageTransformer
import kotlinx.android.synthetic.main.activity_showcase_menu.*

class ShowcaseMenu : BaseActivity(), GreedyGameAdsEventsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_menu)
        initViewPager()
        setClickListeners()
        //Registering the event receiver for this class to the BaseClass
        if(GreedyGameAds.isSdkInitialized) {
           hideLoader()
        }
        else {
            showLoader()
            BaseApplication.initAds(this)
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

    override fun onInitSuccess() {
            hideLoader()
    }

    override fun onInitFailed(cause: InitErrors) {
            showLoader()
    }

    override fun onDestroyed() {

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
            dots_indicator.setViewPager2(this)
        }
    }
}
