package com.alexkn.syntact.app

import android.content.Context
import com.alexkn.syntact.data.config.DaoModule
import com.alexkn.syntact.data.config.DatabaseModule
import com.alexkn.syntact.presentation.common.ViewModelFactory
import com.alexkn.syntact.presentation.deckboard.DeckBoardViewModel
import com.alexkn.syntact.presentation.deckcreation.DeckCreationViewModel
import com.alexkn.syntact.presentation.deckdetails.DeckDetailsViewModel
import com.alexkn.syntact.presentation.decklist.DeckListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

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

    fun flashcardViewModelFactory(): ViewModelFactory<DeckBoardViewModel>

    fun bucketDetailsViewModelFactory(): ViewModelFactory<DeckDetailsViewModel>

    fun createTemplateViewModelFactory(): ViewModelFactory<DeckCreationViewModel>
}

interface ApplicationComponentProvider {
    val applicationComponent: ApplicationComponent
}