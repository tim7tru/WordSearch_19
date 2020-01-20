package com.example.timmytruong.wordsearch_19.viewmodel

import androidx.lifecycle.ViewModel
import com.example.timmytruong.wordsearch_19.provider.InformationBarProvider
import javax.inject.Inject

class InformationBarViewModel(private val informationBarProvider: InformationBarProvider): ViewModel()
{
    fun setScore(resetScore: Boolean)
    {
        informationBarProvider.setScore(resetScore = resetScore)
    }

    fun setTotalWords(totalWords: Int)
    {
        informationBarProvider.setTotalWords(totalCount = totalWords)
    }

    fun getScore(): Int
    {
        return informationBarProvider.getScore()
    }

    fun getTotal(): Int
    {
        return informationBarProvider.getTotalWords()
    }
}