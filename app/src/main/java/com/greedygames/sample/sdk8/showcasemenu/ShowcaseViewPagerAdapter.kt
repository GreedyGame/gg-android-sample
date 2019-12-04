package com.greedygames.sample.sdk8.showcase.nongames.travel_app.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.R
import kotlinx.android.synthetic.main.showcase_item_points.view.*

class ShowcaseViewPagerAdapter:RecyclerView.Adapter<ShowcaseViewPagerAdapter.ViewHolder>() {

    private val data = listOf(

        ShowCasePagerItem(
            title = "For Publishers",
            desc = "Solving two key issues was important for native to be a viable ad strategy for publishers – placement rule-set and fill-rate. With our product suite, publishers \n" +
                    "can now implement native ads with ease, automate design optimization to improve CTR for a considerable jump in revenue.",
            icon = R.drawable.publisher
        ),
        ShowCasePagerItem(
            title = "For Advertiser",
            desc = "User experience is key for us. Any ad from our platform is quality, compliant, relevant and most importantly opt-in. This results into effective outcome metrics for the advertiser and non-intrusive ads for the user automatically.",
            icon = R.drawable.publisher
        )


    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.showcase_item_points, parent, false))

    }


    override fun getItemCount(): Int  = data.size

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bind(
            listItem: ShowCasePagerItem) {
            view.targetIcon.setImageDrawable(ContextCompat.getDrawable(view.context,listItem.icon))
            view.target.text = listItem.title
            view.targetDesc.text = listItem.desc
        }

    }

}

data class ShowCasePagerItem(val title:String,val desc:String,val icon:Int)

