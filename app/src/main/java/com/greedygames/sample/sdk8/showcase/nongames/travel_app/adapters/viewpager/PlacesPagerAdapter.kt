package com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.loadAd
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.loadImage
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.AdPagerItem
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.BaseItem
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.ItemTypes
import com.greedygames.sample.sdk8.showcase.nongames.travel_app.model.PlacesPagerItem
import kotlinx.android.synthetic.main.places_pager_ad_item.view.*
import kotlinx.android.synthetic.main.places_pager_item.view.*
import kotlinx.android.synthetic.main.places_pager_item.view.container

class PlacesPagerAdapter(private val onPageClick:(item:PlacesPagerItem)->Unit):RecyclerView.Adapter<PlacesPagerAdapter.ViewHolder>() {

    /**
     * This ad unit is inserted in between the data set. You can add multiple ad units based on your requirements.
     */
    private val AD_UNIT_4343 = "float-4343"

    private val originalData = listOf(
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
            adValue = AD_UNIT_4343
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
            adValue = AD_UNIT_4343
        )

    )
    var data = listOf<BaseItem>()

    init {
        //Not required if data is loaded from another source.
        filterData()
    }

    /**
     * This function will filter data based on the campaign availability. Each time a refresh is called on GreedyGame agent
     * data will be filtered based on campaign status.
     */
    fun filterData(){
        data = if(!BaseActivity.mGreedyGameAgent.isCampaignAvailable){
            originalData.filter {
                it.itemType == ItemTypes.CONTENT
            }
        }else
            originalData
        notifyDataSetChanged()
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position],onPageClick)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    }

    /**
     * @param position Indicates position of the currently itreating element in the data list
     * Use getItemViewType to decide which view to be inflated to  keep ad view and content view separate.
     */
    override fun getItemViewType(position: Int): Int {
        return when(data[position].itemType){
            ItemTypes.AD->{R.layout.places_pager_ad_item}
            ItemTypes.CONTENT->{R.layout.places_pager_item}
        }
    }

    override fun getItemCount(): Int  = data.size

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bind(
            listItem: BaseItem,
            onPageClick: (item: PlacesPagerItem) -> Unit
        ) {
            when(listItem.itemType){
                ItemTypes.AD->{
                    if(!BaseActivity.mGreedyGameAgent.isCampaignAvailable) {
                        view.adUnit.loadAd(
                            listItem.value,
                            BaseActivity.mGreedyGameAgent,
                            view.context
                        )
                        view.adUnit.scaleType = ImageView.ScaleType.FIT_CENTER
                        view.container.setOnClickListener {
                            BaseActivity.mGreedyGameAgent.showUII(listItem.value)
                        }
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

