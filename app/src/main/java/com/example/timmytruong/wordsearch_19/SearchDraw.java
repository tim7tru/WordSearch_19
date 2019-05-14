package com.example.timmytruong.wordsearch_19;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SearchDraw extends View{

    /**
     * Helper Variables
     */
    private float LINE_WIDTH_1 = 35.0f;
    private float LINE_WIDTH_2 = 55.0f;
    public Paint paint;
    private float startingX, startingY, endingX, endingY;

    /**
     * DESCRIPTION: SearchDraw constructor that sets the initial settings of the paint object
     * @param context: passes in context of the activity in which to draw
     */
    public SearchDraw (Context context) {
        super(context);
        paint = new Paint();
        paint.setStrokeWidth(LINE_WIDTH_1);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(50);
    }

    /**
     * DESCRIPTION: Coordinates of the draw to begin
     * @param startX: Starting X Coordinate
     * @param startY: Starting Y Coordinate
     * @param endX: Ending X Coordinate
     * @param endY: Ending Y Coordinate
     * @param colour: Colour of paint depending on if the search was correct/if the search was even finished
     */
    public void drawLine(float startX, float startY, float endX, float endY, int colour) {
        startingX = startX;
        startingY = startY;
        endingX = endX;
        endingY = endY;
        switch (colour) {
            case 0:
                paint.setStrokeWidth(LINE_WIDTH_2);
                paint.setColor(Color.YELLOW);
                paint.setAlpha(90);
                break;
            case 1:
                paint.setColor(Color.GREEN);
                paint.setAlpha(50);
                break;
        }
        invalidate();
    }

    /**
     * DESCRIPTION: Draws the line!
     * @param canvas: Canvas on which the paint is used to draw on!
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(startingX, startingY, endingX, endingY, paint);
    }
}
