package com.greedygames.sample.sdk8.showcase.nongames.travel_app

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.recyclerview.NewPlacesAdapter
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager.PlacesPagerAdapter
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.fragments.PlaceDetailFragment
import kotlinx.android.synthetic.main.activity_showcase_menu.*
import kotlinx.android.synthetic.main.activity_travel_dashboard.*

class TravelDashboard : BaseActivity(),
    PlaceDetailFragment.OnFragmentInteractionListener {

    private val frameHolderId = 2567
    private val AD_UNIT_4347 = "float-4347"

    val placesPagerAdapter:PlacesPagerAdapter = PlacesPagerAdapter{
        val fragment =
            PlaceDetailFragment.newInstance(it);
        fragment.enterTransition = Slide(Gravity.BOTTOM)
        supportFragmentManager
            .beginTransaction().replace(frameHolderId,fragment).addToBackStack("").commit()
    }

    val newPlacesAdapter = NewPlacesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_dashboard)
        mBaseCampaignStateListener.receiver = TravelDashboardCampaignListener()
        initViews()
    }

    private fun initViews(){
        setupViewpager()
        setupRecyclerView()
        val frameHolder = FrameLayout(this)
        frameHolder.id = frameHolderId
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        root.addView(frameHolder,layoutParams)

        tabAd.loadTextAd(AD_UNIT_4347)
        tabAd.setOnClickListener {
            mGreedyGameAgent.showUII(AD_UNIT_4347)
        }
    }

    private fun setupRecyclerView() {
        with(newPlacesRv){
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            adapter = newPlacesAdapter
        }
    }

    private fun setupViewpager(){
        with(suggestionsPager){
            adapter = placesPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer(MarginPageTransformer(10))
            setPageTransformer(SizeReductionPageTransformer())
        }
    }

    /**
     * TravelDashboardCampaignListener listens to event from SDK via BaseCampaignListener and filters data
     * to the list with or without ads if campaign is available or not available respectively.
     */
    private inner class TravelDashboardCampaignListener:CampaignStateListener{
        fun callFilters(){
            tabAd.loadTextAd(AD_UNIT_4347)
            placesPagerAdapter.filterData()
            newPlacesAdapter.filterData()
        }
        override fun onUnavailable() {
            loader.visibility = View.INVISIBLE
            callFilters()
        }

        override fun onAvailable(p0: String?) {
            loader.visibility  = View.GONE
            callFilters()
        }

        override fun onError(p0: String?) {
            callFilters()
        }

    }
}
