package com.greedygame.sample.sdk8.utils

import android.content.Context
import android.graphics.Color



fun getMaterialColor(context:Context,hardness:Int):Int{
    var returnColor = Color.WHITE;
    val arrayID = context.resources.getIdentifier("mdcolor_$hardness","array",context.applicationContext.packageName);
    if(arrayID!= 0){
        val colors = context.resources.obtainTypedArray(arrayID)
        val index = (Math.random() * colors.length()).toInt()
        returnColor = colors.getColor(index, Color.BLACK)
        colors.recycle()
    }
    return returnColor
}