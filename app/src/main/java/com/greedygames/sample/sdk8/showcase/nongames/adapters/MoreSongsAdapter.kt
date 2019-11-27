package com.greedygames.sample.sdk8.showcase.nongames.adapters

import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
import com.greedygames.sample.sdk8.showcase.nongames.ListItem
import com.greedygames.sample.sdk8.showcase.nongames.fullapp.MoreSongsListFragment
import kotlinx.android.parcel.Parcelize

class MoreSongsAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {
    val data = mutableListOf<MoreSongsData>(
        MoreSongsData("FireFlies - When Chai met Toast",
            cover = "https://source.unsplash.com/random/720*720",
            listItem = ItemTypes.CONTENT),
        MoreSongsData("FireFlies - When Chai met Toast",
            cover = "https://source.unsplash.com/random/720*720",
            listItem = ItemTypes.CONTENT),
        MoreSongsData("FireFlies - When Chai met Toast",
            cover = "https://source.unsplash.com/random/720*720",
            listItem = ItemTypes.CONTENT),
        MoreSongsData("FireFlies - When Chai met Toast",
            cover = "https://source.unsplash.com/random/720*720",
            listItem = ItemTypes.CONTENT),
        MoreSongsData("FireFlies - When Chai met Toast",
            cover = "https://source.unsplash.com/random/720*720",
            listItem = ItemTypes.AD,
            adUnitId = "float-4343")
    )
    override fun getItem(position: Int): Fragment {
        return MoreSongsListFragment.newInstance(data[position])
    }

    override fun getCount(): Int  = data.size
}
@Parcelize
data class MoreSongsData(val title:String,val cover:String,val listItem:ItemTypes,var adUnitId:String = ""):Parcelable