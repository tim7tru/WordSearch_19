package com.example.timmytruong.wordsearch_19.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.adapters.GridAdapter
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.interfaces.InformationBarHandler
import com.example.timmytruong.wordsearch_19.utils.AppConstants
import com.example.timmytruong.wordsearch_19.utils.DrawUtils
import com.example.timmytruong.wordsearch_19.utils.ui.LetterAdapter
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.InformationBarViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : Activity()
{

    private var gridAdapter: GridAdapter by Delegates.notNull()

    private var gridViewModel: GridViewModel by Delegates.notNull()

    private var informationBarViewModel: InformationBarViewModel by Delegates.notNull()

    private val gridHandler = object : GridHandler {

        override fun strikeOutWord(context: Context, word: String, tableLayout: TableLayout)
        {
            for (eachRow in 0 until tableLayout.childCount)
            {
                val rowView: View = tableLayout.getChildAt(eachRow)

                if (rowView is TableRow)
                {
                    val tableRow: TableRow = rowView

                    for (eachCell in 0 until 2)
                    {
                        val textView: TextView = tableRow.getChildAt(eachCell) as TextView

                        if (textView.text.toString().equals(word))
                        {
                            val text: TextView = textView

                            text.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        }
                    }
                }
            }
        }

        override fun setOnTouchListener(context: Context, onTouchListener: View.OnTouchListener, gridView: GridView)
        {
            gridView.setOnTouchListener(onTouchListener)
        }

        override fun setLetters(context: Context, letterAdapter: LetterAdapter, gridView: GridView)
        {
            gridView.adapter = letterAdapter
        }

        override fun setTableLayout(context: Context, keySet: Set<String>, tableLayout: TableLayout)
        {
            var wordCounter: Int = 0

            var tableRow: TableRow = TableRow(context)

            for (word in keySet)
            {
                if (wordCounter % 2 == 0)
                {
                    tableRow = TableRow(context)

                    val rowParams: TableRow.LayoutParams = TableRow.LayoutParams()
                    rowParams.height = TableRow.LayoutParams.WRAP_CONTENT
                    rowParams.width = TableRow.LayoutParams.MATCH_PARENT
                    tableRow.layoutParams = rowParams
                }

                val wordText: TextView = TextView(context)

                val textParams: TableRow.LayoutParams = TableRow.LayoutParams()
                textParams.weight = 0.5 as Float
                textParams.height = TableLayout.LayoutParams.WRAP_CONTENT
                textParams.width = 0
                wordText.layoutParams = textParams

                wordText.textSize = 16 as Float
                wordText.text = word
                wordText.setPadding(0,10,0,10)
                wordText.gravity = Gravity.CENTER

                if (wordText.parent != null)
                {
                    (wordText.parent as ViewGroup).removeView(wordText)
                }
                if (tableRow.parent != null)
                {
                    (tableRow.parent as ViewGroup).removeView(tableRow)
                }

                tableRow.addView(wordText)

                tableLayout.addView(tableRow)

                wordCounter++
            }
        }

        override fun clearTableLayout(context: Context, tableLayout: TableLayout)
        {
            tableLayout.removeAllViews()
        }

        override fun removeSearchView(context: Context, startViewCount: Int, endViewCount: Int,
                                      gridFrame: FrameLayout)
        {
            gridFrame.removeViews(startViewCount, endViewCount - startViewCount)
        }

        override fun displayWinDialogue(context: Context)
        {
            val builder = AlertDialog.Builder(context)

            builder.setTitle(R.string.win_title)
            builder.setMessage(R.string.win_message)
            builder.setPositiveButton(
                    R.string.play_again) { dialog, which ->
                TODO("Implement Reset")
            }
            builder.setNegativeButton(R.string.cancel) { _, _ -> }
            builder.setIcon(android.R.drawable.ic_dialog_info)
            builder.show()
        }

        override fun getLetterAdapter(context: Context, letters: ArrayList<Char>): LetterAdapter
        {
            return LetterAdapter(context, letters)
        }
    }

    private var informationBarHandler = object : InformationBarHandler
    {
        override fun setResetClickListener(onClickListener: View.OnClickListener, resetBTN: Button)
        {
            resetBTN.setOnClickListener(onClickListener)
        }

        override fun setScoreTextView(score: Int, total: Int, scoreView: TextView)
        {
            val text = "$score/$total"
            scoreView.text = text
        }

        override fun setPlusClickListener(context: Context, wordsAL: ArrayList<String>, plusBTN: Button)
        {
            val plusClickListener: View.OnClickListener = View.OnClickListener { v: View ->
                val intent: Intent = Intent(context, EditWordsActivity::class.java)
                intent.putStringArrayListExtra(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY, wordsAL)
                startActivityForResult(intent, 0)
            }
            plusBTN.setOnClickListener(plusClickListener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridViewModel = GridViewModel()
        gridAdapter = GridAdapter(this, gridHandler, gridView, gridFrame, wordTableLayout, gridViewModel)

        informationBarViewModel = InformationBarViewModel()

        gridAdapter.setupWords()
        gridAdapter.setupGrid()
        gridAdapter.setupWordGrid()
        gridAdapter.setupUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0 && data != null)
        {
            val words: ArrayList<String> = data.getStringArrayListExtra(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY)
            TODO("Implement Reset")
        }
        else
        {
            TODO("Implement Reset")
        }
    }

    private val onTouchListener = View.OnTouchListener { v: View, event: MotionEvent ->

        val action: Int = event.actionMasked

        val grid: GridView = v as GridView

        val drawUtils = DrawUtils(this)

        val x: Int = event.x.toInt()

        val y: Int = event.y.toInt()

        val position: Int = grid.pointToPosition(x, y)

        val globalX: Int?

        val globalY: Int?

        val centreX: Int?

        val centreY: Int?

        val startCentreX: Int?

        val startCentreY: Int?

        val endCentreX: Int?

        val endCentreY: Int?

        val endViewNumber: Int?

        val startViewNumber: Int?

        val words: HashMap<String, Boolean> = gridViewModel.getWordsHashMap()

        var currentPosition = -1

        var formedWord = ""

        if (position >= 0 && position < 100)
        {
            val cellView: TextView = grid.findViewWithTag(position)

            val cellViewRect: Rect = Rect()

            cellView.getDrawingRect(cellViewRect)

            grid.offsetDescendantRectToMyCoords(cellView, cellViewRect)

            globalX = cellViewRect.left
            globalY = cellViewRect.top

            centreX = globalX + cellView.width / 2
            centreY = globalY + cellView.height / 2

            gridView.addView(drawUtils)

            when (action)
            {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE -> {
                    if (currentPosition != position)
                    {
                        v.parent.requestDisallowInterceptTouchEvent(true)

                        formedWord += cellView.text.toString()

                        when (action)
                        {
                            MotionEvent.ACTION_DOWN -> {
                                startViewNumber = gridFrame.childCount

                                startCentreX = globalX + cellView.width / 2
                                startCentreY = globalY + cellView.height / 2

                                drawUtils.drawLine(startCentreX.toFloat(), startCentreY.toFloat(), centreX.toFloat(), centreY.toFloat(), 0)
                            }
                            MotionEvent.ACTION_MOVE -> {
                                drawUtils.drawLine(centreX.toFloat(), centreY.toFloat(), centreX.toFloat(), centreY.toFloat(), 0)
                            }
                        }
                    }
                    currentPosition = position
                }
                MotionEvent.ACTION_UP -> {
                    endViewNumber = gridFrame.childCount

                    if (words.contains(formedWord))
                    {
                        if (!(words.get(formedWord) as Boolean))
                        {

                        }
                    }
                }
            }


        }

        true
    }
}