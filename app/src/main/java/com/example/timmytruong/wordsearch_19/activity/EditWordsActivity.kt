package com.example.timmytruong.wordsearch_19.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants
import kotlinx.android.synthetic.main.activity_edit_words.*
import java.util.*

class EditWordsActivity : Activity()
{
    private var words: HashMap<String, Boolean>? = linkedMapOf()

    private var editTexts: ArrayList<EditText> = arrayListOf()

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_words)

        val intent: Intent = intent

        @Suppress("UNCHECKED_CAST")
        words = intent.getSerializableExtra(
                AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY) as HashMap<String, Boolean>

        if (words == null || words!!.isEmpty())
        {
            finish()
        }
        else if (words != null)
        {
            editTexts.add(editText)
            editTexts.add(editText2)
            editTexts.add(editText3)
            editTexts.add(editText4)
            editTexts.add(editText5)
            editTexts.add(editText6)
            editTexts.add(editText7)
            editTexts.add(editText8)

            for (i in 0 until words!!.size)
            {
                editTexts[i].setText(words!!.keys.elementAt(i))
            }

            cancelBTN.setOnClickListener {
                setResult(1)
                finish()
            }

            saveBTN.setOnClickListener {
                words!!.clear()

                for (i in 0 until editTexts.size)
                {
                    if (editTexts[i].text.toString() != "" && editTexts[i].text.toString().length <= 10)
                    {
                        words!![editTexts[i].text.toString().toUpperCase()] = false
                    }
                    else if (editTexts[i].text.toString().length > 10)
                    {
                        Toast.makeText(this, editTexts[i].text.toString() + " is too long, please keep words under 10 characters.", Toast.LENGTH_SHORT).show()
                    }
                }

                val intentWithResult = Intent()
                intentWithResult.putExtra(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY, words)
                setResult(0, intentWithResult)
                finish()
            }
        }
    }
}