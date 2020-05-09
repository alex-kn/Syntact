package dev.alexknittel.syntact.app.general.config

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.alexknittel.syntact.app.auth.config.AndroidTestAuthenticationModule
import dev.alexknittel.syntact.data.config.AndroidTestDaoModule
import dev.alexknittel.syntact.data.config.AndroidTestDatabaseModule
import dev.alexknittel.syntact.service.config.AndroidTestNetworkModule
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
