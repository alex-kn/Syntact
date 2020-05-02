package com.alexkn.syntact.app

import android.content.Context
import com.alexkn.syntact.app.general.config.ApplicationComponent
import com.alexkn.syntact.data.config.DaoModule
import com.alexkn.syntact.service.config.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, AndroidTestDatabaseModule::class, DaoModule::class])
@Singleton
interface AndroidTestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AndroidTestApplicationComponent
    }

}
