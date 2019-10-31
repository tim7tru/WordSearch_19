package com.example.timmytruong.wordsearch_19.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

class CustomSquareView : TextView
{
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        val gridWidth = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        setMeasuredDimension(gridWidth, gridWidth)
    }
}