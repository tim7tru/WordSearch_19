package com.example.timmytruong.wordsearch_19.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.adapters.GridAdapter
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.interfaces.InformationBarHandler
import com.example.timmytruong.wordsearch_19.utils.AppConstants
import com.example.timmytruong.wordsearch_19.utils.ui.LetterAdapter
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.InformationBarViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity()
{
    private val gridViewModel: GridViewModel = GridViewModel()

    private val informationBarViewModel: InformationBarViewModel = InformationBarViewModel()

    private val gridHandler = object : GridHandler
    {

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

        override fun setOnTouchListener(context: Context, onTouchListener: View.OnTouchListener,
                                        gridView: GridView)
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
                textParams.weight = 0.5.toFloat()
                textParams.height = TableLayout.LayoutParams.WRAP_CONTENT
                textParams.width = 0
                wordText.layoutParams = textParams

                wordText.textSize = 16.toFloat()
                wordText.text = word
                wordText.setPadding(0, 10, 0, 10)
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

        override fun getLetterAdapter(context: Context,
                                      letters: LinkedHashMap<Int, Char>): LetterAdapter
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

        override fun setPlusClickListener(context: Context, wordsHM: LinkedHashMap<String, Boolean>,
                                          plusBTN: Button)
        {
            val plusClickListener: View.OnClickListener = View.OnClickListener { v: View ->
                val intent = Intent(context, EditWordsActivity::class.java)
                intent.putExtra(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY, wordsHM)
                startActivityForResult(intent, 0)
            }
            plusBTN.setOnClickListener(plusClickListener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridAdapter = GridAdapter(this, gridHandler, gridView as GridView,
                gridFrame as FrameLayout, wordTableLayout as TableLayout, score as TextView,
                gridViewModel, informationBarHandler, informationBarViewModel)

        gridAdapter.setupWordGrid()
        gridAdapter.setupUI()
        informationBarHandler.setScoreTextView(informationBarViewModel.getScore(), informationBarViewModel.getTotal(), score)
//        TODO("INFORMATION BAR IMPLEMENTATION")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0 && data != null)
        {
            val words: ArrayList<String> = data.getStringArrayListExtra(
                    AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY)
//            TODO("Implement Reset")
        }
        else
        {
//            TODO("Implement Reset")
        }
    }
}