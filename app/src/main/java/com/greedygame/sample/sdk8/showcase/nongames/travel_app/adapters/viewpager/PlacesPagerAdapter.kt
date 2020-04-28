package com.greedygame.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.core.adview.interfaces.AdLoadCallback
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.adview.modals.RefreshPolicy
import com.greedygame.sample.sdk8.BaseActivity
import com.greedygame.sample.sdk8.R
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.model.AdPagerItem
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.model.BaseItem
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.model.ItemTypes
import com.greedygame.sample.sdk8.showcase.nongames.travel_app.model.PlacesPagerItem
import com.greedygame.sample.sdk8.utils.loadImage
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


    /**
     * This function will filter data based on the campaign availability. Each time a refresh is called on GreedyGame agent
     * data will be filtered based on campaign status.
     */
    fun filterData(){
//        Log.d("CAMPAIGN_AVAILABLE","Called with ${BaseActivity.mGreedyGameAgent.isCampaignAvailable}")
//        data = if(!BaseActivity.mGreedyGameAgent.isCampaignAvailable){
//            originalData.filter {
//                it.itemType == ItemTypes.CONTENT
//            }
//        }else
        data = originalData
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

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view), AdLoadCallback {
        fun bind(
            listItem: BaseItem,
            onPageClick: (item: PlacesPagerItem) -> Unit
        ) {
            when(listItem.itemType){
                ItemTypes.AD->{
                    view.adUnit.loadAd(this)
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

        override fun onAdLoaded() {
            Log.d("PagerAd","Ad Loaded")
        }

        override fun onAdLoadFailed(cause: AdRequestErrors) {
            Log.d("PagerAd","Ad Load Failed $cause")
        }

        override fun onUiiOpened() {
            Log.d("PagerAd","Ad uii opened")
        }

        override fun onUiiClosed() {
            Log.d("PagerAd","Ad uii closed")
        }

        override fun onReadyForRefresh() {
            Log.d("PagerAd","Ad ready for refresh")
        }

    }

}

