package com.greedygame.sample.sdk8.showcase.nongames.travel_app.fragments

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.greedygame.sample.sdk8.BaseActivity
import com.greedygame.sample.sdk8.R
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.model.PlacesPagerItem
import com.greedygame.sample.sdk8.utils.loadAd
import com.greedygame.sample.sdk8.utils.loadImage
import kotlinx.android.synthetic.main.fragment_place_detail.*

private const val ARG_PARAM1 = "param1"

class PlaceDetailFragment : Fragment() {
    private var param1: PlacesPagerItem? = null

    private var listener: OnFragmentInteractionListener? = null
    val alphaValueAnimator = ValueAnimator.ofFloat(1f,0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable<PlacesPagerItem>(ARG_PARAM1)

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
        with("float-4346") {
            ctaAdUnit.loadAd(this,BaseActivity.mGreedyGameAgent, context!!)
            ctaAdUnit.setOnClickListener {
                BaseActivity.mGreedyGameAgent.showUII(this)
            }
            topAdUnit.loadAd(
                this,
                BaseActivity.mGreedyGameAgent,
                context!!
            )
            topAdUnit.setOnClickListener {
                BaseActivity.mGreedyGameAgent.showUII(this)
            }
            param1?.let {
                title.text = it.title.replace("\n", " ")
                location.text = it.location
                heroImage.loadImage(it.value)
            }
        }
        setupScrollView()

    }

    private fun setupScrollView(){


        val scrollViewRect = Rect()

        val  visibilityController = VisiblityController{
            if(it == View.VISIBLE){
                topAdUnit?.animate()?.translationY(0f)?.duration = 1000
            }
            else{
                topAdUnit?.animate()?.translationY(-1000f)?.duration = 1000
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

    data class VisiblityController(private var currentVisiblity:Int = -999,var onChangeCallback:(newVisibility:Int)->Unit){
        fun update(newVisibility: Int){
            if(newVisibility != currentVisiblity){
                currentVisiblity = newVisibility
                onChangeCallback(currentVisiblity)
            }
        }
    }



    override fun onStop() {
        super.onStop()
        alphaValueAnimator.cancel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

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
}
