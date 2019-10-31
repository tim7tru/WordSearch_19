package com.example.timmytruong.wordsearch_19.adapters

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.TableLayout
import android.widget.TextView
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.interfaces.InformationBarHandler
import com.example.timmytruong.wordsearch_19.utils.DrawUtils
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.InformationBarViewModel

class DrawAdapter(private val context: Context,
                  private val gridFrame: FrameLayout,
                  private val wordsTableLayout: TableLayout,
                  private val scoreTextView: TextView,
                  private val gridHandler: GridHandler,
                  private val gridViewModel: GridViewModel,
                  private val informationBarViewModel: InformationBarViewModel,
                  private val informationBarHandler: InformationBarHandler)
{
    private var currentPosition: Int? = -1

    private var centreX: Int? = -1

    private var centreY: Int? = -1

    private var startCentreX: Int? = -1

    private var startCentreY: Int? = -1

    private var endViewNumber: Int? = -1

    private var startViewNumber: Int? = -1

    private var formedWord: String = ""

    private val onTouchListener = View.OnTouchListener { v: View, event: MotionEvent ->

        val words: HashMap<String, Boolean> = gridViewModel.getWordsHashMap()

        val action: Int = event.actionMasked

        val grid: GridView = v as GridView

        val x: Int = event.x.toInt()

        val y: Int = event.y.toInt()

        val position: Int = grid.pointToPosition(x, y)

        val globalX: Int?

        val globalY: Int?

        var drawUtils: DrawUtils

        if (position in 0..99)
        {
            val cellView: TextView = grid.findViewWithTag(position)

            val cellViewRect = Rect()

            cellView.getDrawingRect(cellViewRect)

            grid.offsetDescendantRectToMyCoords(cellView, cellViewRect)

            globalX = cellViewRect.left
            globalY = cellViewRect.top

            centreX = globalX + cellView.width / 2
            centreY = globalY + cellView.height / 2

            drawUtils = DrawUtils(context)

            gridFrame.addView(drawUtils)

            when (action)
            {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE ->
                {
                    if (currentPosition != position)
                    {
                        v.parent.requestDisallowInterceptTouchEvent(true)

                        formedWord += cellView.text.toString()

                        when (action)
                        {
                            MotionEvent.ACTION_DOWN ->
                            {
                                startViewNumber = gridFrame.childCount

                                startCentreX = globalX + cellView.width / 2
                                startCentreY = globalY + cellView.height / 2

                                drawUtils.drawLine(startCentreX!!.toFloat(),
                                        startCentreY!!.toFloat(), centreX!!.toFloat(),
                                        centreY!!.toFloat(), 0)
                            }
                            MotionEvent.ACTION_MOVE ->
                            {
                                drawUtils.drawLine(centreX!!.toFloat(), centreY!!.toFloat(),
                                        centreX!!.toFloat(), centreY!!.toFloat(), 0)
                            }
                        }
                    }
                    currentPosition = position
                }
                MotionEvent.ACTION_UP ->
                {
                    endViewNumber = gridFrame.childCount

                    if (words.contains(formedWord) && !(words[formedWord] as Boolean))
                    {
                        informationBarViewModel.setScore(false)

                        informationBarHandler.setScoreTextView(informationBarViewModel.getScore(),
                                informationBarViewModel.getTotal(), scoreTextView)

                        drawUtils = DrawUtils(context)

                        gridFrame.addView(drawUtils)

                        drawUtils.drawLine(startCentreX!!.toFloat(), startCentreY!!.toFloat(),
                                centreX!!.toFloat(), centreY!!.toFloat(), 1)

                        words[formedWord] = true

                        gridViewModel.setWordsHashMap(words)

                        gridHandler.strikeOutWord(context, formedWord, wordsTableLayout)

                        if (informationBarViewModel.getScore() == informationBarViewModel.getTotal())
                        {
                            gridHandler.displayWinDialogue(context)
                        }
                    }

                    gridHandler.removeSearchView(context, startViewNumber!!.minus(1),
                            endViewNumber!!, gridFrame)

                    formedWord = ""
                }
                MotionEvent.ACTION_CANCEL ->
                {
                    formedWord = ""
                }
            }
        }
        else if (position == -1 && action == MotionEvent.ACTION_UP)
        {
            endViewNumber = gridFrame.childCount

            if (words.contains(formedWord) && !(words[formedWord] as Boolean))
            {
                informationBarViewModel.setScore(false)

                informationBarHandler.setScoreTextView(informationBarViewModel.getScore(),
                        informationBarViewModel.getTotal(), scoreTextView)

                drawUtils = DrawUtils(context)

                gridFrame.addView(drawUtils)

                drawUtils.drawLine(startCentreX!!.toFloat(), startCentreY!!.toFloat(),
                        centreX!!.toFloat(), centreY!!.toFloat(), 1)

                words[formedWord] = true

                gridViewModel.setWordsHashMap(words)

                gridHandler.strikeOutWord(context, formedWord, wordsTableLayout)

                if (informationBarViewModel.getScore() == informationBarViewModel.getTotal())
                {
                    gridHandler.displayWinDialogue(context)
                }
            }

            gridHandler.removeSearchView(context, startViewNumber!!.minus(1), endViewNumber!!,
                    gridFrame)

            formedWord = ""
        }

        true
    }

    fun getOnTouchListener(): View.OnTouchListener
    {
        return onTouchListener
    }
}