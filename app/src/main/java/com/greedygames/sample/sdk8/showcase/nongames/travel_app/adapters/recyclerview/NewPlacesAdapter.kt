package com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
import com.greedygames.sample.sdk8.showcase.nongames.loadImage
import com.greedygames.sample.sdk8.showcase.nongames.loadWithRoundedCorners
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.AdPagerItem
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.BaseItem
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.PlacesPagerItem
import kotlinx.android.synthetic.main.new_places_rv_item.view.*

class NewPlacesAdapter:RecyclerView.Adapter<NewPlacesAdapter.ViewHolder>() {

    val data = mutableListOf(
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "Causeaway Island",
            location = "Ireland",
            heroUrl = "https://i.imgur.com/0a6l6n2.png"

        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "Castle",
            location = "Iceland",
            heroUrl = "https://i.imgur.com/uNcRope.png"

        ),
        AdPagerItem(
            ItemTypes.AD,
            adValue = "float-4343"
        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "River Aga",
            location = "Venice",
            heroUrl = "https://i.imgur.com/BihS6yR.png"

        ),
        AdPagerItem(
            ItemTypes.AD,
            adValue = "float-4343"
        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "The Mosque",
            location = "Turkey",
            heroUrl = "https://i.imgur.com/7tPNcqK.png"

        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "The Valley",
            location = "Baghdad",
            heroUrl = "https://i.imgur.com/dgzviKz.png"

        )
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position,data[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position].itemType){
            ItemTypes.AD->{ R.layout.new_places_ad_item}
            ItemTypes.CONTENT->{R.layout.new_places_rv_item}

        }
    }

    override fun getItemCount(): Int  = data.size

    class ViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        fun bind(position: Int, baseItem: BaseItem) {
            when(baseItem.itemType){
                ItemTypes.AD->{
                    view.placeImage.loadWithRoundedCorners(baseItem.value,BaseActivity.mGreedyGameAgent,view.context)
                    view.placeImage.setOnClickListener {
                        BaseActivity.mGreedyGameAgent.showUII(baseItem.value)
                    }
                }
                ItemTypes.CONTENT->{
                    val dataItem = baseItem as PlacesPagerItem
                    view.place.text = dataItem.location
                    view.placeName.text = dataItem.title
                    view.placeImage.loadImage(dataItem.value,false)
                }

            }
        }

    }

}