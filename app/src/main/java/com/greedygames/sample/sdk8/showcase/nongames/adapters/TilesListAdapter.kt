package com.greedygames.sample.sdk8.showcase.nongames.adapters
//
//import android.app.Activity
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.greedygame.android.agent.GreedyGameAgent
//import com.greedygames.sample.sdk8.R
//import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
//import com.greedygames.sample.sdk8.showcase.nongames.ListItem
//import com.greedygames.sample.sdk8.showcase.nongames.loadAd
//import com.squareup.picasso.MemoryPolicy
//import com.squareup.picasso.NetworkPolicy
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.tiles_list_item.view.*
//
//class TilesListAdapter(private val mGreedyGameAgent: GreedyGameAgent,private val activity: Activity,private val mContext:Context) : RecyclerView.Adapter<TilesListAdapter.ViewHolder>() {
//
//    private var data = listOf(
//        ListItem(
//            ItemTypes.CONTENT,
//            "https://source.unsplash.com/random/200x200"
//        ),
//        ListItem(
//            ItemTypes.AD,
//            "float-4196"
//        ),
//        ListItem(
//            ItemTypes.CONTENT,
//            "https://source.unsplash.com/random/200x200"
//        ),
//        ListItem(
//            ItemTypes.AD,
//            "float-4296"
//        )
//    )
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.tiles_list_item, parent, false)
//        )
//    }
//
//    override fun getItemCount() = 15
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int){
//        val newPos = position%(data.size-1)
//        if(newPos < data.size)
//            holder.bind(data[newPos])
//    }
//
//    fun swapData(data: List<ListItem>) {
//        this.data = data
//        notifyDataSetChanged()
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(item: ListItem) = with(itemView) {
//            when(item.type){
//                ItemTypes.AD ->
//                {
//                    content.loadAd(item.value,mGreedyGameAgent,mContext,activity)
//                    content.setOnClickListener {
//                        mGreedyGameAgent.showUII(item.value)
//                    }
//                }
//                else->
//                {
//                    Picasso.with(mContext).load(item.value).
//                    memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE)
//                    .networkPolicy(NetworkPolicy.NO_STORE,NetworkPolicy.NO_CACHE)
//                    .into(content)
//                    content.setOnClickListener(null)
//                }
//            }
//        }
//    }
//}