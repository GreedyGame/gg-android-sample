package com.greedygames.sample.sdk8.showcase.nongames.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
import com.greedygames.sample.sdk8.showcase.nongames.ListItem
import com.greedygames.sample.sdk8.showcase.nongames.loadWithRoundedCorners
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.rect_stories_item.view.*

class RectStoriesAdapter(private val greedyGameAgent: GreedyGameAgent, private val activity: Activity, private val onClickListener:(unitId:String)->Unit):
    RecyclerView.Adapter<RectStoriesAdapter.ViewHolder>(){

    val data = listOf(
        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),

        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),
        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),

        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),
        ListItem(
            ItemTypes.AD,
            "https://source.unsplash.com/random/360x250",
            "float-4343"
        ),
        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),

        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),
        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),

        ListItem(
            ItemTypes.CONTENT,
            "https://source.unsplash.com/random/360x250"
        ),
        ListItem(
            ItemTypes.AD,
            "https://source.unsplash.com/random/360x250",
            "float-4343"
        )

    )

    private lateinit var mContext: Context;
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rect_stories_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newPos = position%(data.size-1)
        if(newPos < data.size)
            holder.bind(data[newPos])
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bind(item: ListItem){
            Picasso.with(mContext)
                .load(item.value)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .transform(RoundedCornersTransformation(10,0))
                .into(view.content)

            view.profileImage.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.user_profile))
            view.profileImage.setOnClickListener(null)

            when(item.type){
//                ItemTypes.CONTENT -> {
//                    view.profileImage.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.user_profile))
//                    view.profileImage.setOnClickListener(null)
//                }
                ItemTypes.AD -> {
//                    view.profileImage.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.user_profile))
//                    view.profileImage.setOnClickListener(null)
                    view.adUnit.loadWithRoundedCorners(item.adValue,greedyGameAgent,mContext,activity)
                    view.adUnit.setOnClickListener {
                        BaseActivity.mGreedyGameAgent.showUII(item.adValue)
                    }

                }
            }
        }
    }

}