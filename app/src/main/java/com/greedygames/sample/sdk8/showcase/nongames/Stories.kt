package com.greedygames.sample.sdk8.showcase.nongames
//
//import android.animation.ObjectAnimator
//import android.animation.ValueAnimator
//import android.app.Activity
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Color
//import android.graphics.drawable.BitmapDrawable
//import android.os.Build
//import android.os.Bundle
//import android.os.CountDownTimer
//import android.util.DisplayMetrics
//import android.util.Log
//import android.util.TypedValue
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.Animation
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.greedygame.android.agent.GreedyGameAgent
//import com.greedygame.android.core.campaign.CampaignStateListener
//import com.greedygames.sample.sdk8.BaseActivity
//import com.greedygames.sample.sdk8.CircularTransform
//import com.greedygames.sample.sdk8.R
//import com.greedygames.sample.sdk8.showcase.nongames.adapters.RectStoriesAdapter
//import com.squareup.picasso.MemoryPolicy
//import com.squareup.picasso.NetworkPolicy
//import com.squareup.picasso.Picasso
//import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
//import kotlinx.android.synthetic.main.activity_stories.*
//import kotlinx.android.synthetic.main.showcase_item.view.*
//import java.io.File
//
//
//class StoriesActivity : BaseActivity() {
//    private val listener = CampaignListener()
//    private var timerObject:CountDownTimer? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_stories)
//    }
//
//
//    override fun onPause() {
//        super.onPause()
//        "Activity Paused".toast(this)
//    }
//    private fun setRectStoriesList(){
//        rectShowCaseList.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
//        rectShowCaseList.adapter = RectStoriesAdapter(mGreedyGameAgent,this){adUnitId->
//            mGreedyGameAgent.showUII(adUnitId)
//        }
//    }
//
//    private fun setList(){
//        showCaseList.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
//        showCaseList.adapter = ShowcaseListAdapter(
//            mGreedyGameAgent,
//            this
//        ) { adUnitId ->
//            mGreedyGameAgent.showUII("unit-4296")
//        }
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        mBaseCampaignStateListener.receiver = listener
//        if(mGreedyGameAgent.isCampaignAvailable) {
//            onCampaignAvailable()
//        }
//    }
//
//    private fun onCampaignAvailable(){
//        setList()
//        setRectStoriesList()
//        startTimer()
//    }
//
//    private fun startTimer(){
//        if(timerObject != null) return
//        timerObject = object:CountDownTimer(60*1000,1000){
//           override fun onTick(millisUntilFinished: Long) {
//               (millisUntilFinished/1000).toString().toast(this@StoriesActivity)
//           }
//
//           override fun onFinish() {
//               mGreedyGameAgent.startEventRefresh()
//              this.start()
//           }
//
//
//       }.start()
//
//    }
//
//    inner class CampaignListener:CampaignStateListener{
//        override fun onUnavailable() {
//            "Campaign Unavailable".toast(this@StoriesActivity)
//        }
//
//        override fun onAvailable(message: String?) {
//            "Campaign Success -> $message".toast(this@StoriesActivity)
//            onCampaignAvailable()
//        }
//
//        override fun onError(error: String?) {
//            "Campaign Error -> $error".toast(this@StoriesActivity)
//        }
//
//    }
//}
//
//fun String.toast(context:Context){
//    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
//}
//
//
//class ShowcaseListAdapter(private val greedyGameAgent: GreedyGameAgent,private val activity: Activity,private val onClickListener:(unitId:String)->Unit):RecyclerView.Adapter<ShowcaseListAdapter.ViewHolder>(){
//
//    val data = listOf(
//        ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),
//        ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),
//        ListItem(
//            ItemTypes.AD,
//            R.drawable.user_profile.toString(),
//            "float-4345"
//        ),
//        ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),
//        ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),ListItem(
//            ItemTypes.CONTENT,
//            R.drawable.user_profile.toString()
//        ),
//        ListItem(
//            ItemTypes.AD,
//            R.drawable.user_profile.toString(),
//            "float-4345"
//        )
//
//    )
//
//    private lateinit var mContext:Context;
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        mContext = parent.context
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.showcase_item,parent,false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val newPos = position%(data.size-1)
//        if(newPos < data.size)
//            holder.bind(data[position])
//    }
//
//    inner class ViewHolder(private val view:View):RecyclerView.ViewHolder(view){
//        fun bind(item: ListItem){
//            when(item.type){
//                ItemTypes.CONTENT -> {
//                    Picasso.with(mContext)
//                        .load(item.value.toInt())
//                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        .transform(CircularTransform())
//                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//                        .placeholder(R.drawable.user_profile)
//                        .noFade()
//                        .into(view.adUnit)
//                    view.adUnit.setOnClickListener(null)
//                    view.highlighter.visibility = View.GONE
//                }
//                ItemTypes.AD -> {
//                    view.highlighter.visibility = View.VISIBLE
//                    rotate(view.highlighter)
//                    view.adUnit.loadAd(item.adValue,greedyGameAgent,mContext,activity,true);
//                    view.adUnit.setOnClickListener {
//                        onClickListener(item.adValue)
//                    }
//                }
//            }
//        }
//
//        private fun rotate(view:ImageView){
//            val rotateAnimator = ObjectAnimator.ofFloat(view,"rotation",0f,360f)
//            rotateAnimator.duration = 2000;
//            rotateAnimator.repeatCount  = Animation.INFINITE
//            rotateAnimator.repeatMode = ValueAnimator.RESTART
//            rotateAnimator.start()
//        }
//    }
//
//}
