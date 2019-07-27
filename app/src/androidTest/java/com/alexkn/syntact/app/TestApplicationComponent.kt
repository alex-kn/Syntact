package com.alexkn.syntact.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [TestRetrofitModule::class, TestDatabaseModule::class])
@Singleton
interface TestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): TestApplicationComponent
    }

}
