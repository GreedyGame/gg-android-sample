package com.greedygame.sample.sdk.showcase.nongames.travel_app

import android.app.AlertDialog
import android.graphics.Color.argb
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.viewpager2.widget.ViewPager2
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.sample.sdk.BaseActivity
import com.greedygame.sample.sdk.showcase.nongames.travel_app.adapters.recyclerview.NewPlacesAdapter
import com.greedygame.sample.sdk.showcase.nongames.travel_app.adapters.viewpager.PlacesPagerAdapter
import com.greedygame.sample.sdk.showcase.nongames.travel_app.fragments.PlaceDetailFragment
import com.greedygame.sample.sdk.utils.getCenterCoordinates
import com.greedygame.sample.sdk.utils.notimportant.Rectangle
import com.greedygame.sample.sdk.utils.notimportant.SharedPrefManager
import com.greedygame.sample.sdk.utils.notimportant.SizeReductionPageTransformer
import com.greedygame.sample.sdk8.R
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.effet.RippleEffect
import com.takusemba.spotlight.shape.Circle
import kotlinx.android.synthetic.main.activity_travel_dashboard.*
import kotlinx.android.synthetic.main.exit_dialouge_header.view.*

class TravelDashboard : BaseActivity() {
    private val frameHolderId = 2567
    private lateinit var spotlight:Spotlight

    private val placesPagerAdapter: PlacesPagerAdapter =
        PlacesPagerAdapter {
            val fragment =
                PlaceDetailFragment.newInstance(
                    it
                );
            fragment.enterTransition = Slide(Gravity.BOTTOM)
            supportFragmentManager
                .beginTransaction().replace(frameHolderId, fragment).addToBackStack("").commit()
        }

    private val newPlacesAdapter = NewPlacesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_dashboard)
        initViews()
    }

    private fun initViews(){
        setupViewpager()
        setupRecyclerView()
        val frameHolder = FrameLayout(this)
        frameHolder.id = frameHolderId
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        root.addView(frameHolder,layoutParams)

        profileImage.setOnClickListener{
//            initCoachmarks()
        }
    }

    private fun initCoachmarks(){
        scrollView.smoothScrollTo(0,0)
        profileImage.postDelayed({
            val restartCoachmarksTarget = getRestartCoachmarksTarget()
            val pagerAdTarget = getPagerAdTarget()
            spotlight = Spotlight.Builder(this)
                .setTargets(pagerAdTarget,restartCoachmarksTarget)
                .setOnSpotlightListener(object : OnSpotlightListener {
                    override fun onEnded() {
                        SharedPrefManager.shouldShowCoachmarks = false
                    }

                    override fun onStarted() {

                    }

                })
                .build()
//            spotlight.start()
        },1000)

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
            //initCoachmarks()

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

                /***
                 * The layout file is configured in
                 * @see R.layout.exit_dialouge_header
                 */
                exitUnit.loadAd(object :AdLoadCallback{
                    override fun onAdLoaded() {
                        Log.d(TAG,"Exit Ad Loaded")
                    }

                    override fun onAdLoadFailed(cause: AdRequestErrors) {
                        Log.d(TAG,"Exit Ad Load Failed - $cause")
                    }

                    override fun onUiiOpened() {
                        Log.d(TAG,"Exit Ad uii opened")
                    }

                    override fun onUiiClosed() {
                        Log.d(TAG,"Exit Ad uii closed")
                    }

                    override fun onReadyForRefresh() {
                        Log.d(TAG,"Exit Ad ready for refresh")
                    }

                })
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
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer(SizeReductionPageTransformer())
            dotsIndicator.setViewPager2(this)
        }
    }

}
