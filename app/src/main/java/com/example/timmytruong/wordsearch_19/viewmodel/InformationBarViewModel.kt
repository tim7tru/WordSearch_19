package com.example.timmytruong.wordsearch_19.viewmodel

import com.example.timmytruong.wordsearch_19.models.InformationBarModel
import com.example.timmytruong.wordsearch_19.provider.InformationBarProvider

class InformationBarViewModel() {

    private val informationBarModel: InformationBarModel = InformationBarModel()

    private var score: Int

    init
    {
        this.score = 0
    }

    fun setScore(resetScore: Boolean)
    {
        when (resetScore)
        {
            true -> {
                score = 0
            }
            false -> {
                score++
            }
        }

        informationBarModel.setScore(score)

    }

    fun setTotalWords(totalWords: Int)
    {
        informationBarModel.setTotalWords(totalWords)
    }

    fun getScore(): Int
    {
        return informationBarModel.getScore()
    }

    fun getTotal(): Int
    {
        return informationBarModel.getTotalWords()
    }

}