package com.alexkn.syntact.app;

import android.app.Application;
import android.content.Context;

import com.alexkn.syntact.domain.usecase.bucket.CreateBucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.domain.usecase.play.FetchPhrasesWorker;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems;
import com.alexkn.syntact.domain.usecase.play.ManageScore;
import com.alexkn.syntact.domain.util.LetterGenerator;
import com.alexkn.syntact.presentation.bucket.create.CreateBucketViewModel;
import com.alexkn.syntact.presentation.bucket.list.ListBucketsViewModel;
import com.alexkn.syntact.presentation.play.board.BoardViewModel;
import com.alexkn.syntact.presentation.play.menu.PlayMenuViewModel;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance Builder applicationContext(Context applicationContext);
        ApplicationComponent build();
    }

    ManageSolvableItems phraseUseCase();

    CreateBucket createBucket();

    LetterGenerator generateCharactersUseCase();

    ManageBuckets languageManagement();

    ManageLetters letterManagement();

    ManageScore manageScore();

    Property property();

    void inject(FetchPhrasesWorker fetchPhrasesWorker);

    ViewModelFactory<BoardViewModel> boardViewModelFactory();
    ViewModelFactory<PlayMenuViewModel> playMenuViewModelFactory();
    ViewModelFactory<CreateBucketViewModel> createBucketViewModelFactory();
    ViewModelFactory<ListBucketsViewModel> listBucketsViewModelFactory();
}


