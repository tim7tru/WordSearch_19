package com.example.timmytruong.wordsearch_19

import android.app.Application
import com.example.timmytruong.wordsearch_19.dagger.component.AppComponent
import com.example.timmytruong.wordsearch_19.dagger.component.DaggerAppComponent

class WordSearchApp: Application()
{
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    private fun createAppComponent()
    {
        appComponent = DaggerAppComponent.builder().build()
    }
}