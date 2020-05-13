package dev.alexknittel.syntact.app.general.config

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.alexknittel.syntact.data.config.TestDaoModule
import dev.alexknittel.syntact.data.config.TestDatabaseModule
import dev.alexknittel.syntact.presentation.deckcreation.DeckCreationViewModelTest
import dev.alexknittel.syntact.service.config.TestNetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Component(modules = [TestNetworkModule::class, TestDatabaseModule::class, TestDaoModule::class])
@Singleton
interface TestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(deckCreationViewModelTest: DeckCreationViewModelTest)

}
