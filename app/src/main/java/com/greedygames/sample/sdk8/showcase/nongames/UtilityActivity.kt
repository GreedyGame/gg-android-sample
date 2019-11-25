package com.greedygames.sample.sdk8.showcase.nongames

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import kotlinx.android.synthetic.main.activity_utility.*

class UtilityActivity : BaseActivity() {
    lateinit var countDownTimer: CountDownTimer;
    private var isCardFlipped = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utility)

        countDownTimer  = object:CountDownTimer(5000,1000){
            override fun onFinish() {
                countDownTimer.start()
                flipAlbumArt()
            }

            override fun onTick(millisUntilFinished: Long) {
                seekBar.progress+=1

            }

        }
        countDownTimer.start()
        refreshAds()
        adUnit.loadAd("unit-4296", mGreedyGameAgent,this,this,false)

    }

    private fun flipAlbumArt(){
//        val firstScaleAnimation = ObjectAnimator.ofFloat(albumArt,"scaleX",1f,0f)
//        val secondScaleAnimation = ObjectAnimator.ofFloat(albumArt,"scaleX",0f,1f)
        val firstRotateAnimation = ObjectAnimator.ofFloat(albumArt,"rotation",1f, 180f)
        val secondRotateAnimation = ObjectAnimator.ofFloat(albumArt,"rotation",180f, 1f)
//        firstScaleAnimation.interpolator = DecelerateInterpolator()
//        secondScaleAnimation.interpolator = AccelerateDecelerateInterpolator()
        firstRotateAnimation.interpolator = DecelerateInterpolator()
        secondRotateAnimation.interpolator = AccelerateDecelerateInterpolator()
//        firstScaleAnimation.duration = 500
//        secondScaleAnimation.duration = 500
        firstRotateAnimation.duration = 1000
        secondRotateAnimation.duration = 1000
        firstRotateAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                    albumArt.loadAd(
                        "float-4191",
                        mGreedyGameAgent,
                        this@UtilityActivity,
                        this@UtilityActivity,
                        false
                    )
                albumArt.rotation = 0f
                albumArt.setOnClickListener { mGreedyGameAgent.showUII("float-4191") }

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    albumArt.setImageDrawable(getDrawable(R.drawable.download))
                } else {
                    albumArt.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@UtilityActivity
                            , R.drawable.download
                        )
                    )
                }
                albumArt.setOnClickListener(null)
            }

        })
        //firstScaleAnimation.start()
        if(isCardFlipped)
            firstRotateAnimation.start()
        else{
            secondRotateAnimation.start()
        }
        isCardFlipped  = !isCardFlipped
    }
}
