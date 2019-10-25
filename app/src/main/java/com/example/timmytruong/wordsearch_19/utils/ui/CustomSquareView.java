package com.example.timmytruong.wordsearch_19.utils.ui;

import android.content.Context;
import android.util.AttributeSet;

public class CustomSquareView extends android.support.v7.widget.AppCompatTextView {

    public CustomSquareView(final Context context) {
        super(context);
    }

    public CustomSquareView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSquareView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int gridWidth = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        setMeasuredDimension(gridWidth, gridWidth);
    }

    @Override
    protected void onSizeChanged(final int width, final int height, final int oldwidth, final int oldheight) {
        super.onSizeChanged(width, width, oldwidth, oldheight);
    }
}