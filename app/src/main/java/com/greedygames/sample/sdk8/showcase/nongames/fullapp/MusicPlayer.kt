package com.greedygames.sample.sdk8.showcase.nongames.fullapp

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.ShowcaseListAdapter
import com.greedygames.sample.sdk8.showcase.nongames.adapters.RectStoriesAdapter
import com.greedygames.sample.sdk8.showcase.nongames.loadAd
import kotlinx.android.synthetic.main.activity_music_player.*
import kotlinx.android.synthetic.main.activity_music_player_content.*
import kotlinx.android.synthetic.main.activity_utility.*
import kotlinx.android.synthetic.main.exit_dialouge_header.view.*
import kotlin.math.abs


class MusicPlayer : AppCompatActivity() {
    private val playerSheetController  = PlayerSheetController()
    private var mSheetBehavior:BottomSheetBehavior<ConstraintLayout>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        setup()
    }

    private fun setup(){
        setupPlayerSheet()
        setupStoryList()
        setRecommendationList()
        setupFAB()
    }

    private fun setupFAB(){
        moreSongsButton.setOnClickListener {
            frame.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frame,MoreSongsFragment()).addToBackStack("MoreSongsFragment").commit()
        }
    }

    private fun setupStoryList(){
        userStoryList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        userStoryList.adapter = ShowcaseListAdapter(
            BaseActivity.mGreedyGameAgent,
            this
        ) { adUnitId ->
            BaseActivity.mGreedyGameAgent.showUII(adUnitId)
        }
    }

    private fun setRecommendationList(){
        recommendationList.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recommendationList.adapter = RectStoriesAdapter(BaseActivity.mGreedyGameAgent,this){ adUnitId->
            BaseActivity.mGreedyGameAgent.showUII(adUnitId)
        }
    }

    private fun setupPlayerSheet(){

        mSheetBehavior = BottomSheetBehavior.from(playerBottomSheet)
        mSheetBehavior?.peekHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,140f,
            DisplayMetrics()
        ).toInt()
        playerTab.setOnClickListener {
            mSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
        mSheetBehavior?.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            var mCurrentPos = 0f
            override fun onSlide(p0: View, currentPosition: Float) {
                mCurrentPos = currentPosition
                moreSongsButton.alpha = abs(1-currentPosition)
                moreSongsButton.isEnabled = 1f==(1-currentPosition)
            }

            override fun onStateChanged(p0: View, state: Int) {
                if(state == BottomSheetBehavior.STATE_EXPANDED){
                    Log.d("JUDE","Expanded PlayerSheer")
                    slideUp(playerTab)
//                    playerTab.visibility = View.INVISIBLE
                    playerSheetController.onExpanded()
                }
                if(state == BottomSheetBehavior.STATE_COLLAPSED){
                    Log.d("JUDE","Collapsed PlayerSheer")
                    slideDown(playerTab)
//                    playerTab.visibility = View.VISIBLE
                    playerSheetController.onCollapsed()
                }

            }

        })
    }
    
    fun slideUp(view:View){
        val slideUpAnimator = ObjectAnimator.ofFloat(view,"translationY",0f,-100f)
        slideUpAnimator.interpolator = AccelerateDecelerateInterpolator()
        slideUpAnimator.duration = 100
        slideUpAnimator.start()
    }
    fun slideDown(view:View){
        val slideDownAnimator = ObjectAnimator.ofFloat(view,"translationY",-100f,0f)
        slideDownAnimator.interpolator = AccelerateDecelerateInterpolator()
        slideDownAnimator.duration = 100
        slideDownAnimator.start()
    }


    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0 && mSheetBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
            showExitAlertDialog {
                super.onBackPressed()
            }
        }
        else{
            if(mSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED)
                mSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            else {
                frame.visibility = View.GONE
                super.onBackPressed()

            }
        }

    }

    private fun showExitAlertDialog(callback:()->Unit){
        val customTitle = layoutInflater.inflate(R.layout.exit_dialouge_header,null,false)
        customTitle.adUnit.loadAd("float-4344",BaseActivity.mGreedyGameAgent,this,this)
        val exitDialog  = AlertDialog.Builder(this)
            .setCustomTitle(customTitle)
            .setPositiveButton("No",null)
            .setNegativeButton("  Yes"
            ) { dialog, which -> callback() }
        exitDialog.show()
    }

    inner class PlayerSheetController{
        private var countDownTimer: CountDownTimer? = null
        private var isCardFlipped = false
        fun onExpanded() {
            if(countDownTimer != null)
                countDownTimer?.cancel()
            countDownTimer = null
            countDownTimer  = object: CountDownTimer(5000,1000){
                override fun onFinish() {
                    countDownTimer?.start()
                    flipAlbumArt()
                }

                override fun onTick(millisUntilFinished: Long) {
                    seekBar.progress+=1

                }

            }
            countDownTimer?.start()

        }

        fun onCollapsed() {
            countDownTimer?.cancel()
            countDownTimer = null
            isCardFlipped = false

        }

        private fun flipAlbumArt(){
//        val firstScaleAnimation = ObjectAnimator.ofFloat(albumArt,"scaleX",1f,0f)
//        val secondScaleAnimation = ObjectAnimator.ofFloat(albumArt,"scaleX",0f,1f)
            val firstRotateAnimation = ObjectAnimator.ofFloat(albumArt,"rotationX",1f, 180f)
            val secondRotateAnimation = ObjectAnimator.ofFloat(bigAdUnit,"rotationX",180f, 1f)
//        firstScaleAnimation.interpolator = DecelerateInterpolator()
//        secondScaleAnimation.interpolator = AccelerateDecelerateInterpolator()
            firstRotateAnimation.interpolator = DecelerateInterpolator()
            secondRotateAnimation.interpolator = AccelerateDecelerateInterpolator()
//        firstScaleAnimation.duration = 500
//        secondScaleAnimation.duration = 500
            firstRotateAnimation.duration = 1000
            secondRotateAnimation.duration = 1000
            bigAdUnit.loadAd(
                "float-4343",
                BaseActivity.mGreedyGameAgent,
                this@MusicPlayer,
                this@MusicPlayer,
                false
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                albumArt.setImageDrawable(getDrawable(R.drawable.download))
            } else {
                albumArt.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MusicPlayer
                        , R.drawable.download
                    )
                )
                albumArt.setOnClickListener(null)
            }

            firstRotateAnimation.addUpdateListener {
                Log.d("JUDE-ANI","Animated Fraction ${it.animatedValue}, ${it.animatedFraction}")
                bigAdUnit.visibility = View.VISIBLE
                bigAdUnit.isEnabled = true
                bigAdUnit.alpha  = it.animatedFraction
                albumArt.alpha = 1 - it.animatedFraction
                if(bigAdUnit.alpha == 1f){
                    albumArt.visibility = View.INVISIBLE
                }
                bigAdUnit.setOnClickListener{
                    BaseActivity.mGreedyGameAgent.showUII("float-4343")
                }
            }

            secondRotateAnimation.addUpdateListener {
                Log.d("ANIMATED-PERCENT","${it.animatedFraction}")
                albumArt.visibility = View.VISIBLE
                albumArt.rotationX = 0f
                albumArt.alpha  = it.animatedFraction
                bigAdUnit.alpha = 1 - it.animatedFraction
                if(albumArt.alpha == 1f){
                    bigAdUnit.visibility = View.INVISIBLE
                    bigAdUnit.isEnabled = false
                }

            }
            firstRotateAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
//                    albumArt.loadAd(
//                        "float-4343",
//                        BaseActivity.mGreedyGameAgent,
//                        this@MusicPlayer,
//                        this@MusicPlayer,
//                        false
//                    )
//                    albumArt.rotationX = 0f
                    albumArt.setOnClickListener { BaseActivity.mGreedyGameAgent.showUII("float-4191") }

                    //secondScaleAnimation.start()
                }

            })
            secondRotateAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {


                }

                override fun onAnimationEnd(animation: Animator?) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        albumArt.setImageDrawable(getDrawable(R.drawable.download))
//                    } else {
//                        albumArt.setImageDrawable(
//                            ContextCompat.getDrawable(
//                                this@MusicPlayer
//                                , R.drawable.download
//                            )
//                        )
//                    }
                    albumArt.setOnClickListener(null)
                }

            })
            //firstScaleAnimation.start()
            isCardFlipped  = !isCardFlipped
            if(!isCardFlipped)
                firstRotateAnimation.start()
            else{
                secondRotateAnimation.start()
            }

        }

    }
}
