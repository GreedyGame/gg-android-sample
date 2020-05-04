package com.greedygame.sample.sdk.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url:String){
    if(url.isNotEmpty()){
        Picasso.with(context).load(url).into(this)
    }

}

fun String.toast(context:Context){
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}

// For Coachmarks Positioning
fun View.getCenterCoordinates():IntArray{
    val viewPosition = IntArray(2)
    getLocationOnScreen(viewPosition)
    viewPosition[0] = viewPosition[0]+width/2
    viewPosition[1] = viewPosition[1]+height/2
    return viewPosition
}
