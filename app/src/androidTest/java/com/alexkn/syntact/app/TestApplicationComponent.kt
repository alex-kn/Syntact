package com.alexkn.syntact.app

import android.content.Context
import com.alexkn.syntact.data.config.DaoModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, TestDatabaseModule::class, DaoModule::class])
@Singleton
interface TestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): TestApplicationComponent
    }

}
