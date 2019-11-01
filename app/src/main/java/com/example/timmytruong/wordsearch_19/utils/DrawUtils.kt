package com.example.timmytruong.wordsearch_19.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants

class DrawUtils(context: Context) : View(context)
{
    private val paint: Paint = Paint()

    private var startingX: Float = -1.0f

    private var startingY: Float = -1.0f

    private var endingX: Float = -1.0f

    private var endingY: Float = -1.0f

    init
    {
        paint.strokeWidth = AppConstants.PAINT_LINE_WIDTH_SEARCH
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.isDither = true
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.alpha = 50
    }

    fun drawLine(startX: Float, startY: Float, endX: Float, endY: Float, colour: Int)
    {
        startingX = startX
        startingY = startY
        endingX = endX
        endingY = endY

        when (colour)
        {
            AppConstants.PAINT_COLOUR_YELLOW ->
            {
                paint.strokeWidth = AppConstants.PAINT_LINE_WIDTH_FOUND
                paint.color = Color.YELLOW
                paint.alpha = 90
            }
            AppConstants.PAINT_COLOUR_GREEN ->
            {
                paint.color = Color.GREEN
                paint.alpha = 50
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?)
    {
        canvas?.drawLine(startingX, startingY, endingX, endingY, paint)
    }
}