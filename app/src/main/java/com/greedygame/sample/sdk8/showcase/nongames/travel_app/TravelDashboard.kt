package com.greedygame.sample.sdk8.showcase.nongames.travel_app

import android.app.AlertDialog
import android.graphics.Color.argb
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.viewpager2.widget.ViewPager2
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygame.sample.sdk8.BaseActivity
import com.greedygame.sample.sdk8.R
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.adapters.recyclerview.NewPlacesAdapter
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager.PlacesPagerAdapter
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.fragments.PlaceDetailFragment
import com.greedygame.sample.sdk8.utils.getCenterCoordinates
import com.greedygame.sample.sdk8.utils.loadAd
import com.greedygame.sample.sdk8.utils.loadTextAd
import com.greedygame.sample.sdk8.utils.notimportant.Rectangle
import com.greedygame.sample.sdk8.utils.notimportant.SharedPrefManager
import com.greedygame.sample.sdk8.utils.notimportant.SizeReductionPageTransformer
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.effet.RippleEffect
import com.takusemba.spotlight.shape.Circle
import kotlinx.android.synthetic.main.activity_travel_dashboard.*
import kotlinx.android.synthetic.main.exit_dialouge_header.view.*

class TravelDashboard : BaseActivity(),
    PlaceDetailFragment.OnFragmentInteractionListener {
    private val ggEventListener = TravelDashboardCampaignListener()
    private val frameHolderId = 2567
    private val AD_UNIT_4347 = "float-4347"
    private lateinit var spotlight:Spotlight;

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
        initViews()
    }

    override fun onResume() {
        super.onResume()
        mBaseCampaignStateListener.receiver = ggEventListener
    }

    override fun onPause() {
        super.onPause()
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
        profileImage.setOnClickListener{
            initCoachmarks()
        }
    }

    private fun initCoachmarks(){
        scrollView.smoothScrollTo(0,0)
        profileImage.postDelayed({
            val restartCoachmarksTarget = getRestartCoachmarksTarget()
            val textAdTarget = getTextAdTarget()
            val pagerAdTarget = getPagerAdTarget()
            spotlight = Spotlight.Builder(this)
                .setTargets(textAdTarget,pagerAdTarget,restartCoachmarksTarget)
                .setOnSpotlightListener(object : OnSpotlightListener {
                    override fun onEnded() {
                        SharedPrefManager.shouldShowCoachmarks = false
                    }

                    override fun onStarted() {

                    }

                })
                .build()
            spotlight.start()
        },1000)

    }


    private fun getTextAdTarget():Target{
        val viewPosition = linearLayout.getCenterCoordinates()
        return Target.Builder()
            .setAnchor(viewPosition[0].toFloat(),viewPosition[1].toFloat())
            .setShape(Rectangle(linearLayout,10))
            .setOverlay(LayoutInflater.from(this).inflate(R.layout.coach_marks_textad_target,null))
            .build()

    }

    private fun getPagerAdTarget():Target{
        val viewPosition = suggestionsPager.getCenterCoordinates()
        return Target.Builder()
            .setAnchor(viewPosition[0].toFloat(),viewPosition[1].toFloat())
            .setShape(Rectangle(suggestionsPager,50))
            .setOverlay(LayoutInflater.from(this).inflate(R.layout.coach_marks_pager_target,null))
            .build()
    }
    private fun  getRestartCoachmarksTarget():Target{
        val viewPosition = profileImage.getCenterCoordinates()
        val radius = profileImage.width/2.toFloat()+50
        return Target.Builder()
            .setAnchor(viewPosition[0].toFloat(),viewPosition[1].toFloat())
            .setShape(Circle(radius))
            .setOverlay(LayoutInflater.from(this).inflate(R.layout.coach_marks_restart_target,null))
            .setEffect(RippleEffect(100f, 200f, argb(30, 124, 255, 90)))
            .build()
    }

    fun showNext(v:View){
        spotlight.next()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus && SharedPrefManager.shouldShowCoachmarks){
            initCoachmarks()

        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0)
            showExitAlert{
                super.onBackPressed()
            }
        else
            super.onBackPressed()
    }
    private fun showExitAlert(callback:()->Unit){
        AlertDialog.Builder(this)
            .setPositiveButton("No",null)
            .setNegativeButton("Yes"){ _, _ ->
                callback()
            }
            .setCustomTitle(LayoutInflater.from(this).inflate(R.layout.exit_dialouge_header,null).apply {
                adUnit.loadAd("float-4346",mGreedyGameAgent,this@TravelDashboard)
                adUnit.setOnClickListener {
                    mGreedyGameAgent.showUII("float-4346")
                }
            })
            .show()
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
            placesPagerAdapter.filterData()
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer(SizeReductionPageTransformer())
            dotsIndicator.setViewPager2(this)
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
            callFilters()
        }

        override fun onAvailable(p0: String?) {
            callFilters()
        }

        override fun onError(p0: String?) {
            callFilters()
        }

    }
}
