package dev.alexknittel.syntact.app.general.config

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.alexknittel.syntact.data.config.DaoModule
import dev.alexknittel.syntact.data.config.DatabaseModule
import dev.alexknittel.syntact.presentation.common.ViewModelFactory
import dev.alexknittel.syntact.presentation.deckboard.DeckBoardViewModel
import dev.alexknittel.syntact.presentation.deckcreation.DeckCreationViewModel
import dev.alexknittel.syntact.presentation.deckdetails.DeckDetailsViewModel
import dev.alexknittel.syntact.presentation.decklist.DeckListViewModel
import dev.alexknittel.syntact.service.config.NetworkModule
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