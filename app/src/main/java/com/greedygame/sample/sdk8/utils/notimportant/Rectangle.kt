package com.greedygame.sample.sdk8.utils.notimportant

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Build
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.takusemba.spotlight.shape.Shape

class Rectangle(private val targetView: View, private val adjustDistance:Int, override val duration: Long = 1000, override val interpolator: TimeInterpolator = AccelerateInterpolator(10f)) :Shape {
    private val rectCordinates = IntArray(2)
    val width:Float
    val height:Float
    init {
        targetView.getLocationOnScreen(rectCordinates)
        rectCordinates[0]
//        -=adjustDistance/2
        rectCordinates[1]
//        -=adjustDistance
        width = targetView.width.toFloat()
//        +adjustDistance
        height = targetView.height.toFloat()
//        +2*adjustDistance
    }

    override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(rectCordinates[0].toFloat(),rectCordinates[1].toFloat(),rectCordinates[0]+width,rectCordinates[1]+height,
                20f,20f,paint
            )
        }else{
            canvas.drawRect(rectCordinates[0].toFloat(),rectCordinates[1].toFloat(),rectCordinates[0]+width,rectCordinates[1]+height,
                paint
            )
        }

    }
}