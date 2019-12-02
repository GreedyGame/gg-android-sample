package com.greedygames.sample.sdk8.showcase.nongames.travel_app

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.recyclerview.NewPlacesAdapter
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager.PlacesPagerAdapter
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.fragments.PlaceDetailFragment
import kotlinx.android.synthetic.main.activity_travel_dashboard.*

class TravelDashboard : AppCompatActivity(),
    PlaceDetailFragment.OnFragmentInteractionListener {

    val frameHolderId = 2567;

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

    }

    private fun setupRecyclerView() {
        with(newPlacesRv){
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            adapter = NewPlacesAdapter()
        }
    }

    private fun setupViewpager(){
        with(suggestionsPager){
            adapter =
                PlacesPagerAdapter(){
                    val fragment =
                        PlaceDetailFragment.newInstance(it);
                    fragment.enterTransition = Slide(Gravity.BOTTOM)
                    supportFragmentManager
                        .beginTransaction().replace(frameHolderId,fragment).addToBackStack("").commit()
                }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer(MarginPageTransformer(10))
            setPageTransformer(SizeReductionPageTransformer() )

        }
    }
}
