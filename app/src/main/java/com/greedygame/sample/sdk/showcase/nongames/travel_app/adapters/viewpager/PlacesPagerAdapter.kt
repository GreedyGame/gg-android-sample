package com.greedygame.sample.sdk.showcase.nongames.travel_app.adapters.viewpager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.AdPagerItem
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.BaseItem
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.ItemTypes
import com.greedygame.sample.sdk.showcase.nongames.travel_app.model.PlacesPagerItem
import com.greedygame.sample.sdk.utils.loadImage
import com.greedygame.sample.sdk8.R
import kotlinx.android.synthetic.main.places_pager_ad_item.view.*
import kotlinx.android.synthetic.main.places_pager_item.view.*
import kotlinx.android.synthetic.main.places_pager_item.view.container

class PlacesPagerAdapter(private val onPageClick:(item: PlacesPagerItem)->Unit):RecyclerView.Adapter<PlacesPagerAdapter.ViewHolder>() {

    private val data = mutableListOf(
        PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/y7v9pCJ.png",
            title = "Antelope\nCanyon",
            location = "Arizona,USA"
        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/JbZ92pE.png",
            title = "Beach\nMaldives",
            location = "Maldives"
        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/ZBFe67z.png",
            title = "Amristar\nFort",
            location = "Amrister,India"
        ),
//        AdPagerItem(ItemTypes.AD),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/T5tPude.png",
            title = "Malibu\nIsland",
            location = "California,USA"
        ),
        PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/v9CS3W3.png",
            title = "Eiffel\nTower",
            location = "Paris,France"
        )
//        , AdPagerItem(ItemTypes.AD)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].let {
            if(it.itemType == ItemTypes.AD)
                holder.bind(AdEventListener(position),it,onPageClick)
            else
                holder.bind(null,it,onPageClick)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
//        TODO Uncomment
//        return when(data[position].itemType){
//            ItemTypes.AD->{
//                R.layout.places_pager_ad_item}
//            ItemTypes.CONTENT->{R.layout.places_pager_item}
//        }
//        TODO Comment
        return R.layout.places_pager_item
    }

    override fun getItemCount(): Int  = data.size

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        fun bind(
            listener: AdEventListener?,
            listItem: BaseItem,
            onPageClick: (item: PlacesPagerItem) -> Unit
        ) {
            when(listItem.itemType){
//                TODO uncomment
//                ItemTypes.AD->{
//                    listener?.let { view.adUnit.loadAd(it) }
//                }
                ItemTypes.CONTENT->{
                    val dataItem = listItem as PlacesPagerItem
                    view.title.text  = dataItem.title
                    view.location.text  = dataItem.location
                    view.heroImage.loadImage(listItem.value)
                    view.container.setOnClickListener { onPageClick(dataItem) }
                }
            }

        }

    }
    inner class AdEventListener(private val position:Int):AdLoadCallback{
        override fun onAdLoadFailed(cause: AdRequestErrors) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }

        override fun onAdLoaded() {
            
        }

        override fun onReadyForRefresh() {
            
        }

        override fun onUiiClosed() {
            
        }

        override fun onUiiOpened() {
            
        }

    }
}

