package com.alexkn.syntact.app;

import android.content.Context;

import com.alexkn.syntact.domain.usecase.bucket.CreateBucketWorker;
import com.alexkn.syntact.domain.usecase.play.FetchPhrasesWorker;
import com.alexkn.syntact.presentation.bucketlist.PlayMenuViewModel;
import com.alexkn.syntact.presentation.createbucket.CreateBucketViewModel;
import com.alexkn.syntact.presentation.flashcard.FlashcardViewModel;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder applicationContext(Context applicationContext);

        ApplicationComponent build();
    }

    void inject(FetchPhrasesWorker fetchPhrasesWorker);

    void inject(CreateBucketWorker createBucketWorker);

    ViewModelFactory<PlayMenuViewModel> playMenuViewModelFactory();

    ViewModelFactory<CreateBucketViewModel> createBucketViewModelFactory();

    ViewModelFactory<FlashcardViewModel> flashcardViewModelFactory();
}


