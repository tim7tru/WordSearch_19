package com.example.timmytruong.wordsearch_19.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.utils.AppConstants
import kotlinx.android.synthetic.main.activity_edit_words.*

class EditWordsActivity : Activity()
{
    private lateinit var words: LinkedHashMap<String, Boolean>

    private var editTexts: ArrayList<EditText> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_words)

        val intent: Intent = getIntent()

        words = intent.getSerializableExtra(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY) as LinkedHashMap<String, Boolean>

        editTexts.add(editText)
        editTexts.add(editText2)
        editTexts.add(editText3)
        editTexts.add(editText4)
        editTexts.add(editText5)
        editTexts.add(editText6)
        editTexts.add(editText7)
        editTexts.add(editText8)

        for (i in 0 until words.size)
        {
            editTexts.get(i).setText(words.keys.elementAt(i))
        }

        cancelBTN.setOnClickListener { v: View ->
            setResult(1)
            finish()
        }

        saveBTN.setOnClickListener { v: View ->
            words.clear()

            for (i in 0 until editTexts.size)
            {
                if (!editTexts.get(i).text.toString().equals("") && editTexts.get(i).text.toString().length <= 10)
                {
                    words.put(editTexts.get(i).text.toString().toUpperCase(), false)
                }
                else if (editTexts.get(i).text.toString().length > 10)
                {
                    Toast.makeText(this, editTexts.get(i).text.toString() + " is too long, please keep words under 10 characters.", Toast.LENGTH_SHORT).show()
                }
            }

            val intentWithResult: Intent = Intent()
            intentWithResult.putExtra(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY, words)
            setResult(0, intentWithResult)
            finish()
        }
    }
}