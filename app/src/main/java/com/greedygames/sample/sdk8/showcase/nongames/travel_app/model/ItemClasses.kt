package com.greedygames.sample.sdk8.showcase.nongames.travel_app.model

import android.os.Parcelable
import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseItem(
    val itemType :ItemTypes,
    val value:String
):Parcelable

@Parcelize
data class PlacesPagerItem(
    val type:ItemTypes,
    val title:String,
    val location:String,
    private val heroUrl:String
):BaseItem(type,heroUrl)

@Parcelize
data class AdPagerItem(
    val type: ItemTypes,
    private val adValue:String
):BaseItem(type,adValue)

