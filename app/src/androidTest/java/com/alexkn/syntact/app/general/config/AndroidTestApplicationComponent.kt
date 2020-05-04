package com.alexkn.syntact.app.general.config

import android.content.Context
import com.alexkn.syntact.app.auth.config.AndroidTestAuthenticationModule
import com.alexkn.syntact.data.config.AndroidTestDaoModule
import com.alexkn.syntact.data.config.AndroidTestDatabaseModule
import com.alexkn.syntact.service.config.AndroidTestNetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AndroidTestAuthenticationModule::class, AndroidTestNetworkModule::class, AndroidTestDatabaseModule::class, AndroidTestDaoModule::class])
@Singleton
interface AndroidTestApplicationComponent : ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AndroidTestApplicationComponent
    }

}
