package com.greedygames.sample.sdk8.showcasemenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.android.core.campaign.CampaignStateListener
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.StoriesActivity
import com.greedygames.sample.sdk8.showcase.nongames.TilesActivity
import com.greedygames.sample.sdk8.showcase.nongames.UtilityActivity
import com.greedygames.sample.sdk8.showcase.nongames.fullapp.MusicPlayer
import com.greedygames.sample.sdk8.showcase.nongames.toast
import kotlinx.android.synthetic.main.activity_showcase_menu.*

class ShowcaseMenu : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_menu)
        setup()
        initAds()
        mBaseCampaignStateListener.receiver = ShowcaseListener()
    }

    private fun setup(){
        nonGamesSelectionList.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        nonGamesSelectionList.adapter = NonGamesMenuAdapter{
            when(it){
                NonGamesTypes.STORIES ->{
                    startActivity(Intent(this,
                        StoriesActivity::class.java))
                }
                NonGamesTypes.TILES ->{
                    startActivity(Intent(this, TilesActivity::class.java))
                }
                NonGamesTypes.UTILITY ->{
                    startActivity(Intent(this,
                        UtilityActivity::class.java))
                }
                NonGamesTypes.FULL ->{
                    startActivity(Intent(this,
                        MusicPlayer::class.java))
                }
            }
        }
        gamesSelectionList.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        gamesSelectionList.adapter = GamesMenuAdapter{
        }
    }

    inner class ShowcaseListener:CampaignStateListener{
        override fun onUnavailable() {
            loader.visibility = View.INVISIBLE
            "Ads are not loaded".toast(this@ShowcaseMenu)
        }

        override fun onAvailable(p0: String?) {
            loader.visibility  = View.GONE

        }

        override fun onError(p0: String?) {
            
        }

    }
}
