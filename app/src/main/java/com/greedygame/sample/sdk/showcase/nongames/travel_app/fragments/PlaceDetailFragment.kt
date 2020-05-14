package com.greedygame.sample.sdk.showcase.nongames.travel_app.fragments

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.PlacesPagerItem
import com.greedygame.sample.sdk.utils.loadImage
import com.greedygame.sample.sdk8.R
import kotlinx.android.synthetic.main.fragment_place_detail.*
import java.lang.Exception

private const val ARG_PARAM1 = "param1"

class PlaceDetailFragment : Fragment(), AdLoadCallback {
    private var param1: PlacesPagerItem? = null

    private val alphaValueAnimator = ValueAnimator.ofFloat(1f, 0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(
                ARG_PARAM1
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitButton.setOnClickListener {
            activity?.onBackPressed()
        }

//     TODO
//        topAdUnit.loadAd(this)

// TODO
        setupScrollView()


        param1?.let {
            title.text = it.title.replace("\n", " ")
            location.text = it.location
            heroImage.loadImage(it.value)
        }


//      TODO
                bannerUnit.loadAd(object : AdLoadCallback {
                    override fun onAdLoadFailed(cause: AdRequestErrors) {
                        try {
                            bannerUnit.visibility = View.GONE
                        }catch (e:Exception){

                        }
                    }

                    override fun onAdLoaded() {

                    }

                    override fun onReadyForRefresh() {

                    }

                    override fun onUiiClosed() {

                    }

                    override fun onUiiOpened() {

                    }

                })

    }

    private fun setupScrollView() {
        val scrollViewRect = Rect()
        val visibilityController =
            VisiblityController {
                if (it == View.VISIBLE) {
//                    topAdUnit?.animate()?.translationY(0f)?.duration = 1000
                } else {
//                    topAdUnit?.animate()?.translationY(-1000f)?.duration = 1000
                }
            }

        visibilityController.update(View.GONE)

        scrollView?.getHitRect(scrollViewRect);
        scrollView?.viewTreeObserver?.addOnScrollChangedListener {

            if (ctaAdUnit?.getLocalVisibleRect(scrollViewRect) == true) {
                Log.d("SCROLL_VI", "VISIBLE")
                visibilityController.update(View.GONE)
            } else {
                visibilityController.update(View.VISIBLE)
                Log.d("SCROLL_VI", "NOT VISIBLE")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        alphaValueAnimator.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance(item: PlacesPagerItem) =
            PlaceDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, item)
                }
            }
    }

    override fun onAdLoaded() {
        Log.d("DetailPage","Ad Loaded")
    }

    override fun onAdLoadFailed(cause: AdRequestErrors) {
        Log.d("DetailPage","Ad Load Failed $cause")

    }

    override fun onUiiOpened() {
        Log.d("DetailPage","Ad uii Opened")
    }

    override fun onUiiClosed() {
        Log.d("DetailPage","Ad uii closed")
    }

    override fun onReadyForRefresh() {
        Log.d("DetailPage","Ad  ready for refresh")
    }

    //Not Important for sdk integration
    data class VisiblityController(
        private var currentVisiblity: Int = -999,
        var onChangeCallback: (newVisibility: Int) -> Unit
    ) {
        fun update(newVisibility: Int) {
            if (newVisibility != currentVisiblity) {
                currentVisiblity = newVisibility
                onChangeCallback(currentVisiblity)
            }
        }
    }
}
