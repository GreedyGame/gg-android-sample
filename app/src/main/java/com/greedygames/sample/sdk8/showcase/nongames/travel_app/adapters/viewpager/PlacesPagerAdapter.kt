package com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
import com.greedygames.sample.sdk8.showcase.nongames.loadAd
import com.greedygames.sample.sdk8.showcase.nongames.loadImage
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.AdPagerItem
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.BaseItem
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.PlacesPagerItem
import kotlinx.android.synthetic.main.places_pager_ad_item.view.*
import kotlinx.android.synthetic.main.places_pager_item.view.*
import kotlinx.android.synthetic.main.places_pager_item.view.container

class PlacesPagerAdapter(private val onPageClick:(item:PlacesPagerItem)->Unit):RecyclerView.Adapter<PlacesPagerAdapter.ViewHolder>() {
    val data = listOf(
        PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/y7v9pCJ.png",
            title = "Antelope\nCanyon",
            location = "Arizona,USA"
        ), PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/JbZ92pE.png",
            title = "Beach\nMaldives",
            location = "Maldives"
        ), PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/ZBFe67z.png",
            title = "Amristar\nFort",
            location = "Amrister,India"
        ), AdPagerItem(
            ItemTypes.AD,
            adValue = "float-4343"
        ), PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/T5tPude.png",
            title = "Malibu\nIsland",
            location = "California,USA"
        ), PlacesPagerItem(
            ItemTypes.CONTENT,
            heroUrl = "https://i.imgur.com/v9CS3W3.png",
            title = "Eiffel\nTower",
            location = "Paris,France"
        ), AdPagerItem(
            ItemTypes.AD,
            adValue = "float-4343"
        )

    )




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position,data[position],onPageClick)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )

    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position].itemType){
            ItemTypes.AD->{R.layout.places_pager_ad_item}
            ItemTypes.CONTENT->{R.layout.places_pager_item}
        }
    }

    override fun getItemCount(): Int  = data.size

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bind(
            position: Int,
            listItem: BaseItem,
            onPageClick: (item: PlacesPagerItem) -> Unit
        ) {
            when(listItem.itemType){
                ItemTypes.AD->{
                    view.adUnit.loadAd(listItem.value,BaseActivity.mGreedyGameAgent,view.context)
                    view.container.setOnClickListener {
                        BaseActivity.mGreedyGameAgent.showUII(listItem.value)
                    }

                }
                ItemTypes.CONTENT->{
                    val dataItem = listItem as PlacesPagerItem;
                    view.title.text  = dataItem.title
                    view.location.text  = dataItem.location
                    view.heroImage.loadImage(listItem.value)
                    view.container.setOnClickListener { onPageClick(dataItem) }
                }
            }

        }

    }

}

