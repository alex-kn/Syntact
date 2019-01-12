package com.alexkn.syntact.domain;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.PhraseUseCase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
class ApplicationModule {

    private Application syntactApplication;

    public ApplicationModule(SyntactApplication syntactApplication) {

        this.syntactApplication = syntactApplication;
    }

    @Provides
    @Singleton
    Application proviateApplication(){
        return syntactApplication;
    }

//    @Provides
//    @Singleton
//    PhraseUseCase providePhraseUseCase(){
//        return new P
//    }
}
