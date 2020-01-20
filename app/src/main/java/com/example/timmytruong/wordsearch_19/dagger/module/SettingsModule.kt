package com.example.timmytruong.wordsearch_19.dagger.module

import com.example.timmytruong.wordsearch_19.provider.SettingsProvider
import com.example.timmytruong.wordsearch_19.viewmodel.factory.SettingsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule
{
    @Singleton
    @Provides
    fun providesSettingsViewModelFactory(settingsProvider: SettingsProvider): SettingsViewModelFactory
    {
        return SettingsViewModelFactory(settingsProvider)
    }

    @Singleton
    @Provides
    fun providesSettingsProvider(): SettingsProvider
    {
        return SettingsProvider()
    }
}