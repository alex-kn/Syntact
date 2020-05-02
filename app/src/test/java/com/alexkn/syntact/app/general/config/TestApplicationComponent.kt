package com.alexkn.syntact.app.general.config

import android.content.Context
import com.alexkn.syntact.app.auth.config.TestAuthenticationModule
import com.alexkn.syntact.data.config.TestDaoModule
import com.alexkn.syntact.data.config.TestDatabaseModule
import com.alexkn.syntact.presentation.deckcreation.DeckCreationViewModelTest
import com.alexkn.syntact.service.config.TestNetworkModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Component(modules = [TestAuthenticationModule::class, TestNetworkModule::class, TestDatabaseModule::class, TestDaoModule::class])
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
