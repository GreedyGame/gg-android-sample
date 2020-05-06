package com.greedygame.sample.sdk.showcasemenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.greedygame.sample.sdk.BaseActivity
import com.greedygame.sample.sdk.showcase.nongames.travel_app.TravelDashboard
import com.greedygame.sample.sdk.utils.notimportant.SizeReductionPageTransformer
import com.greedygame.sample.sdk.utils.toast
import com.greedygame.sample.sdk8.R
import kotlinx.android.synthetic.main.activity_showcase_menu.*

class ShowcaseMenu : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_menu)
        initViewPager()
        setClickListeners()
        hideLoader()
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
            dots_indicator.setViewPager2(this)
        }
    }
}
