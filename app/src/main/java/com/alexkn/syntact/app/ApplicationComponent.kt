package com.alexkn.syntact.app

import android.content.Context

import com.alexkn.syntact.presentation.deckdetails.DeckDetailsViewModel
import com.alexkn.syntact.presentation.decklist.DeckListViewModel
import com.alexkn.syntact.presentation.deckselection.DeckSelectionViewModel
import com.alexkn.syntact.presentation.deckcreation.DeckCreationViewModel
import com.alexkn.syntact.presentation.deckboard.DeckBoardViewModel

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, DaoModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }

    fun playMenuViewModelFactory(): ViewModelFactory<DeckListViewModel>

    fun createBucketViewModelFactory(): ViewModelFactory<DeckSelectionViewModel>

    fun flashcardViewModelFactory(): ViewModelFactory<DeckBoardViewModel>

    fun bucketDetailsViewModelFactory(): ViewModelFactory<DeckDetailsViewModel>

    fun createTemplateViewModelFactory(): ViewModelFactory<DeckCreationViewModel>
}


