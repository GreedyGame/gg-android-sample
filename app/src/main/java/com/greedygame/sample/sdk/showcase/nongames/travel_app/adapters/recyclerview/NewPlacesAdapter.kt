package com.greedygame.sample.sdk.showcase.nongames.travel_app.adapters.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.AdPagerItem
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.BaseItem
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.ItemTypes
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.PlacesPagerItem
import com.greedygame.sample.sdk.utils.loadImage
import com.greedygame.sample.sdk8.R
import kotlinx.android.synthetic.main.new_places_ad_item.view.*
import kotlinx.android.synthetic.main.new_places_rv_item.view.*

class NewPlacesAdapter:RecyclerView.Adapter<NewPlacesAdapter.ViewHolder>() {
    /**
    The list data represents your apps data for the recyclerview. When loading data from an api, insert ad objects
    within the data at predetermined positions like every 5th position. In this example it is every 3rd position.
    ** IMPORTANT **
    When displaying admob ads make sure that there is only one unit visible on the screen at any time.
     */
    private val data = mutableListOf(
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "Causeaway",
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
            ItemTypes.AD
        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            "River Aga",
            location = "Venice",
            heroUrl = "https://i.imgur.com/BihS6yR.png"

        ),
        AdPagerItem(
            ItemTypes.AD
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
            holder.bind(data[position])
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

    class ViewHolder(private val view: View):RecyclerView.ViewHolder(view), AdLoadCallback {
        fun bind(baseItem: BaseItem) {
            when(baseItem.itemType){
                ItemTypes.AD->{
                    view.placeImageAd.loadAd(this)
                }
                ItemTypes.CONTENT->{
                    view.placeImage.scaleType = ImageView.ScaleType.FIT_XY
                    val dataItem = baseItem as PlacesPagerItem
                    view.place.text = dataItem.location
                    view.placeName.text = dataItem.title
                    view.placeImage.loadImage(dataItem.value)
                }
            }
        }

        override fun onAdLoaded() {
            Log.d("NewPlacesAdapter","AdLoaded")
        }

        override fun onAdLoadFailed(cause: AdRequestErrors) {
            view.placeImageAd.visibility = View.GONE
            Log.d("NewPlacesAdapter","AdLoadFailed $cause")
        }

        override fun onUiiOpened() {
            Log.d("NewPlacesAdapter","Uii Opened")
        }

        override fun onUiiClosed() {
            Log.d("NewPlacesAdapter","Uii Closed")
        }

        override fun onReadyForRefresh() {
            Log.d("NewPlacesAdapter","Ready for refresh")
        }
    }

}