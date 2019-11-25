package com.greedygames.sample.sdk8.showcasemenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.utils.getMaterialColor
import kotlinx.android.synthetic.main.showcase_menu_item.view.*

class NonGamesMenuAdapter (private val onClickListener:(selectedItem: NonGamesTypes)->Unit) : RecyclerView.Adapter<NonGamesMenuAdapter.NonGamesViewHolder>() {

    private var data: List<NonGamesTypes> = listOf(
        NonGamesTypes.STORIES,
        NonGamesTypes.TILES,
        NonGamesTypes.FULL,
        NonGamesTypes.UTILITY
    )
    private lateinit var mContext: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonGamesViewHolder {
        mContext = parent.context
        return NonGamesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.showcase_menu_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NonGamesViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<NonGamesTypes>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class NonGamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NonGamesTypes) = with(itemView) {
            itemView.menuItem.text=item.value
            itemView.container.setBackgroundColor(getMaterialColor(mContext,600))
            setOnClickListener {
                    onClickListener(item)
            }
        }
    }

}
enum class NonGamesTypes(val value:String){
    STORIES("Stories"),
    TILES("Tiles"),
    UTILITY("Utility"),
    FULL("Full App")
}