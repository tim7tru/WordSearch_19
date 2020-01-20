package com.example.timmytruong.wordsearch_19.dagger.module

import com.example.timmytruong.wordsearch_19.provider.InformationBarProvider
import com.example.timmytruong.wordsearch_19.viewmodel.factory.InformationBarViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InformationBarModule
{
    @Singleton
    @Provides
    fun providesInformationBarViewModelFactory(informationBarProvider: InformationBarProvider): InformationBarViewModelFactory
    {
        return InformationBarViewModelFactory(informationBarProvider = informationBarProvider)
    }

    @Singleton
    @Provides
    fun providesInformationBarProvider(): InformationBarProvider
    {
        return InformationBarProvider()
    }
}