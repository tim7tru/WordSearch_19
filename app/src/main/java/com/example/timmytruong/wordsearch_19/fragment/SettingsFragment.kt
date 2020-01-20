package com.example.timmytruong.wordsearch_19.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.timmytruong.wordsearch_19.R
import com.example.timmytruong.wordsearch_19.dagger.component.DaggerAppComponent
import com.example.timmytruong.wordsearch_19.interfaces.SaveHandler
import com.example.timmytruong.wordsearch_19.model.Word
import com.example.timmytruong.wordsearch_19.utils.constant.AppConstants
import com.example.timmytruong.wordsearch_19.viewmodel.SettingsViewModel
import com.example.timmytruong.wordsearch_19.viewmodel.factory.SettingsViewModelFactory
import kotlinx.android.synthetic.main.fragment_edit_words.*
import javax.inject.Inject

class SettingsFragment(private val saveHandler: SaveHandler) : Fragment()
{
    @Inject lateinit var settingsViewModelFactory: SettingsViewModelFactory

    private lateinit var settingsViewModel: SettingsViewModel

    private var editTexts: ArrayList<EditText> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_edit_words, container, false) as ConstraintLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerAppComponent.create().inject(this)

        settingsViewModel = settingsViewModelFactory.create(SettingsViewModel::class.java)

        val array: ArrayList<Word> = arguments?.getSerializable(AppConstants.INTENT_EXTRA_WORDS_ARRAY_LIST_KEY) as ArrayList<Word>

        settingsViewModel.setSavedWords(array)

        if (settingsViewModel.getSavedWords().isEmpty())
        {
            finish()
        }
        else
        {
            editTexts.add(editText)
            editTexts.add(editText2)
            editTexts.add(editText3)
            editTexts.add(editText4)
            editTexts.add(editText5)
            editTexts.add(editText6)
            editTexts.add(editText7)
            editTexts.add(editText8)

            for (i in 0 until settingsViewModel.getSavedWords().size)
            {
                editTexts[i].setText(settingsViewModel.getSavedWords()[i].word)
            }

            cancelBTN.setOnClickListener {
                finish()
            }

            saveBTN.setOnClickListener {
                settingsViewModel.clearSavedWords()

                for (i in 0 until editTexts.size)
                {
                    if (editTexts[i].text.toString() != "" && editTexts[i].text.toString().length <= 10)
                    {
                        settingsViewModel.addSavedWord(Word(word = editTexts[i].text.toString().toUpperCase(), beenFound = false, index = i))
                    }
                    else if (editTexts[i].text.toString().length > 10)
                    {
                        Toast.makeText(activity, editTexts[i].text.toString() + " is too long, please keep words under 10 characters.", Toast.LENGTH_SHORT).show()
                    }
                }

                saveHandler.onSaveClicked()

                finish()
            }
        }
    }

    private fun finish()
    {
        if (activity != null)
        {
            activity!!.supportFragmentManager.beginTransaction().hide(this).commit()
        }
    }
}
