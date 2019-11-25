package com.greedygames.sample.sdk8.showcasemenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.utils.getMaterialColor
import kotlinx.android.synthetic.main.showcase_menu_item.view.*

class GamesMenuAdapter (private val onClickListener:(selectedItem: GamesTypes)->Unit) : RecyclerView.Adapter<GamesMenuAdapter.GamesViewHolder>() {

    private var data: List<GamesTypes> = GamesTypes.values().asList()
    private lateinit var mContext: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesMenuAdapter.GamesViewHolder {
        mContext = parent.context
        return GamesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.showcase_menu_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<GamesTypes>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class GamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GamesTypes) = with(itemView) {
            itemView.menuItem.text=item.value.capitalize()
            itemView.container.setBackgroundColor(getMaterialColor(mContext,400))
            setOnClickListener {
                onClickListener(item)
            }
        }
    }

}
enum class GamesTypes(val value:String){
    ACTION("ACTION"),
    ADVENTURE("ADVENTURE"),
    ARCADE("ARCADE"),
    BOARD("BOARD"),
    CARD("CARD"),
    CASINO("CASINO"),
    CASUAL("CASUAL"),
    ENTERTAINMENT("ENTERTAINMENT"),
    PUZZLE("PUZZLE"),
    RACING("RACING"),
    SIMULATION("SIMULATION"),
    SPORTS("SPORTS"),
    STRATEGY("STRATEGY"),
    TRIVIA("TRIVIA"),
    WORD("WORD"),

}