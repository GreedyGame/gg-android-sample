package com.greedygames.sample.sdk8.showcase.nongames.travel_app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.greedygame.android.agent.GreedyGameAgent
import com.greedygames.sample.sdk8.BaseActivity
import com.greedygames.sample.sdk8.CircularTransform
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

fun ImageView.loadAd(unitId:String, greedyGameAgent: GreedyGameAgent, context: Context, useCircularTransform:Boolean = false){
    if(!BaseActivity.mGreedyGameAgent.isCampaignAvailable){
        //Hides is the view if a campaign is not available
        this.visibility = View.GONE
    }
    else{
        this.visibility = View.VISIBLE
    }
    if(unitId.isNotEmpty()){
        val adPath = greedyGameAgent.getPath(unitId)
        if(adPath.isNotEmpty()){
            val adFile = File(adPath)
            val picasso = Picasso.with(context).load(adFile)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            if(useCircularTransform) {
                picasso.transform(CircularTransform())
            }
            picasso.noFade().into(this)
        }
    }



}
fun ImageView.loadImage(url:String){
    if(url.isNotEmpty()){
        Picasso.with(context).load(url).into(this)
    }

}

fun ImageView.loadTextAd(unitId:String){
    //Hides is the view if a campaign is not available
    if(!BaseActivity.mGreedyGameAgent.isCampaignAvailable){
        this.visibility = View.GONE
    }
    else{
        this.visibility = View.VISIBLE
    }
    if(unitId.isNotEmpty()) {
        val adPath = BaseActivity.mGreedyGameAgent.getPath(unitId)
        val bitmap = BitmapFactory.decodeFile(adPath)
        bitmap?.let {
            val copy = it.copy(Bitmap.Config.ARGB_8888,true)
            this.setImageBitmap(copy)
        }

    }

}

fun Bitmap.replaceColor(context: Context, fromAsRGB:IntArray, toAsColor:Int): BitmapDrawable {
    fun match(pixel:Int):Boolean{
        return Math.abs(Color.red(pixel) - fromAsRGB[0])  == 0 &&
                Math.abs(Color.green(pixel) - fromAsRGB[1])  == 0 &&
                Math.abs(Color.blue(pixel) - fromAsRGB[2]) == 0
    }

    val copy = copy(Bitmap.Config.ARGB_8888,true)
    for(x in 0 until copy.width){
        for(y in 0 until copy.height){
            if(match(copy.getPixel(x,y))){
                copy.setPixel(x,y,toAsColor)
            }
        }
    }
    return BitmapDrawable(context.resources,copy)

}

fun ImageView.loadWithRoundedCorners(unitId:String, greedyGameAgent: GreedyGameAgent, context: Context){
    //Hides is the view if a campaign is not available
    if(!BaseActivity.mGreedyGameAgent.isCampaignAvailable){
        this.visibility = View.GONE
    }
    else{
        this.visibility = View.VISIBLE
    }
    if(unitId.isNotEmpty()){
        val adPath = greedyGameAgent.getPath(unitId);
        if(adPath.isNotEmpty()){
            val adFile = File(adPath)
            val picasso = Picasso.with(context).load(adFile)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            picasso.transform(RoundedCornersTransformation(10,0))
            picasso.into(this)
        }
    }

}

fun String.toast(context:Context){
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}
