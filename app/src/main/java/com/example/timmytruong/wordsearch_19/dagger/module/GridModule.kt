package com.example.timmytruong.wordsearch_19.dagger.module

import com.example.timmytruong.wordsearch_19.provider.GridProvider
import com.example.timmytruong.wordsearch_19.viewmodel.factory.GridViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class GridModule
{
    @Reusable
    @Provides
    fun providesGridViewModelFactory(gridProvider: GridProvider): GridViewModelFactory
    {
        return GridViewModelFactory(gridProvider)
    }

    @Reusable
    @Provides
    fun providesGridProvider(): GridProvider
    {
        return GridProvider()
    }
}