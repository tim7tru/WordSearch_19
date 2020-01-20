package com.example.timmytruong.wordsearch_19.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.adapters.GridAdapter
import com.example.timmytruong.wordsearch_19.dagger.component.DaggerAppComponent
import com.example.timmytruong.wordsearch_19.fragment.SettingsFragment
import com.example.timmytruong.wordsearch_19.interfaces.GridHandler
import com.example.timmytruong.wordsearch_19.interfaces.InformationBarHandler
import com.example.timmytruong.wordsearch_19.interfaces.SaveHandler
import com.example.timmytruong.wordsearch_19.model.Letter
import com.example.timmytruong.wordsearch_19.model.Word
import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants
import com.example.timmytruong.wordsearch_19.utils.ui.LetterAdapter
import com.example.timmytruong.wordsearch_19.viewmodel.GridViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.InformationBarViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.factory.GridViewModelFactory
import com.example.timmytruong.wordsearch_19.viewmodel.factory.InformationBarViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity()
{
    @Inject lateinit var gridViewModelFactory: GridViewModelFactory

    @Inject lateinit var informationBarViewModelFactory: InformationBarViewModelFactory

    private lateinit var gridViewModel: GridViewModel

    private lateinit var informationBarViewModel: InformationBarViewModel

    private lateinit var gridAdapter: GridAdapter

    private lateinit var settingsFragment: SettingsFragment

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

                    for (eachCell in 0 until rowView.childCount)
                    {
                        val textView: TextView = tableRow.getChildAt(eachCell) as TextView

                        if (textView.text.toString() == word)
                        {
                            val text: TextView = textView

                            text.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        }
                    }
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun setOnTouchListener(context: Context,
                                        onTouchListener: View.OnTouchListener,
                                        gridView: GridView)
        {
            gridView.setOnTouchListener(onTouchListener)
        }

        override fun setLetters(context: Context,
                                letterAdapter: LetterAdapter,
                                gridView: GridView)
        {
            gridView.adapter = letterAdapter
        }

        override fun setTableLayout(context: Context,
                                    keySet: ArrayList<Word>,
                                    tableLayout: TableLayout)
        {

            tableLayout.removeAllViews()

            var tableRow = TableRow(context)

            for ((wordCounter, word) in keySet.withIndex())
            {
                if (wordCounter % 2 == 0)
                {
                    tableRow = TableRow(context)

                    val rowParams: TableRow.LayoutParams = TableRow.LayoutParams()
                    rowParams.height = TableRow.LayoutParams.WRAP_CONTENT
                    rowParams.width = TableRow.LayoutParams.MATCH_PARENT
                    tableRow.layoutParams = rowParams
                }

                val wordText = TextView(context)

                val textParams: TableRow.LayoutParams = TableRow.LayoutParams()
                textParams.weight = 0.5.toFloat()
                textParams.height = TableLayout.LayoutParams.WRAP_CONTENT
                textParams.width = 0
                wordText.layoutParams = textParams

                wordText.textSize = 16.toFloat()
                wordText.text = word.word
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

            }
        }

        override fun clearTableLayout(context: Context,
                                      tableLayout: TableLayout)
        {
            tableLayout.removeAllViews()
        }

        override fun removeSearchView(context: Context,
                                      startViewCount: Int,
                                      endViewCount: Int,
                                      gridFrame: FrameLayout)
        {
            gridFrame.removeViews(startViewCount, endViewCount - startViewCount)
        }

        override fun displayWinDialogue(context: Context)
        {
            val builder = AlertDialog.Builder(context)
                    .setTitle(R.string.win_title)
                    .setMessage(R.string.win_message)
                    .setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton(R.string.play_again)
            { _, _ ->
                gridAdapter.reset()
                reset()
            }
            builder.setNegativeButton(R.string.cancel) { _, _ -> }
            builder.show()
        }

        override fun getLetterAdapter(context: Context,
                                      letters: ArrayList<Letter>): LetterAdapter
        {
            return LetterAdapter(context = context,
                    letters = letters)
        }
    }

    private var informationBarHandler: InformationBarHandler = object : InformationBarHandler
    {
        override fun setResetClickListener(resetBTN: Button)
        {
            val resetClickListener = View.OnClickListener {
                gridAdapter.reset()

                reset()
            }
            resetBTN.setOnClickListener(resetClickListener)

        }

        override fun setScoreTextView(score: Int, total: Int, scoreView: TextView)
        {
            val text = "$score/$total"
            scoreView.text = text
        }

        override fun setPlusClickListener(context: Context, words: ArrayList<Word>,
                                          plusBTN: Button)
        {
            val plusClickListener = View.OnClickListener {

                val bundle = Bundle()

                bundle.putSerializable(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY, gridViewModel.getWords())

                settingsFragment.arguments = bundle

                this@MainActivity.supportFragmentManager.beginTransaction()
                        .show(settingsFragment)
                        .commit()
            }
            plusBTN.setOnClickListener(plusClickListener)
        }
    }

    private val saveHandler: SaveHandler = object: SaveHandler
    {
        override fun onSaveClicked()
        {
            gridAdapter.reset()

            reset()
        }

    }

    @Suppress("PLUGIN_WARNING")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        DaggerAppComponent.create().inject(this)

        gridViewModel = gridViewModelFactory.create(GridViewModel::class.java)

        informationBarViewModel = informationBarViewModelFactory.create(InformationBarViewModel::class.java)

        settingsFragment = SettingsFragment(saveHandler = saveHandler)

        val bundle = Bundle()

        bundle.putSerializable(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY, gridViewModel.getWords())

        settingsFragment.arguments = bundle

        this@MainActivity.supportFragmentManager.beginTransaction()
                .add(main_screen.id, settingsFragment, AppConstants.FRAGMENT_EDIT_WORDS)
                .hide(settingsFragment)
                .commit()

        gridAdapter = GridAdapter(context = this,
                gridHandler = gridHandler,
                gridView = gridView as GridView,
                gridFrameLayout = gridFrame as FrameLayout,
                wordTableLayout = wordTableLayout as TableLayout,
                scoreTextView = score as TextView,
                gridViewModel = gridViewModel,
                informationBarHandler = informationBarHandler,
                informationBarViewModel = informationBarViewModel)

        gridAdapter.setupWordGrid()

        gridAdapter.setupUI()

        informationBarHandler.setScoreTextView(score = informationBarViewModel.getScore(),
                total = informationBarViewModel.getTotal(),
                scoreView = score)

        informationBarHandler.setResetClickListener(resetBTN = resetBTN)

        informationBarHandler.setPlusClickListener(context = this,
                words = gridViewModel.getWords(),
                plusBTN = plusBTN)
    }

    private fun reset()
    {
        informationBarViewModel.setScore(resetScore = true)

        informationBarHandler.setScoreTextView(score = informationBarViewModel.getScore(),
                total = informationBarViewModel.getTotal(),
                scoreView = score)

        gridHandler.removeSearchView(context = this,
                startViewCount = 1,
                endViewCount = gridFrame.childCount,
                gridFrame = gridFrame)
    }
}