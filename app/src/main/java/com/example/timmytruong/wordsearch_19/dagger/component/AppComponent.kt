package com.example.timmytruong.wordsearch_19.dagger.component

import com.example.timmytruong.wordsearch_19.activity.MainActivity
import com.example.timmytruong.wordsearch_19.dagger.module.GridModule
import com.example.timmytruong.wordsearch_19.dagger.module.InformationBarModule
import com.example.timmytruong.wordsearch_19.fragment.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GridModule::class, InformationBarModule::class])
interface AppComponent
{
    fun inject(mainActivity: MainActivity)

    fun inject(settingsFragment: SettingsFragment)
}