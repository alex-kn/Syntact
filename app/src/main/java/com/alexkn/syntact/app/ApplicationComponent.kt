package com.alexkn.syntact.app

import android.content.Context

import com.alexkn.syntact.domain.worker.FetchPhrasesWorker
import com.alexkn.syntact.presentation.bucketdetails.BucketDetailsViewModel
import com.alexkn.syntact.presentation.bucketlist.PlayMenuViewModel
import com.alexkn.syntact.presentation.createbucket.CreateBucketViewModel
import com.alexkn.syntact.presentation.createtemplate.CreateTemplateViewModel
import com.alexkn.syntact.presentation.flashcard.FlashcardViewModel

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Component(modules = [RetrofitModule::class, DatabaseModule::class, DaoModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }

    fun inject(fetchPhrasesWorker: FetchPhrasesWorker)

    fun playMenuViewModelFactory(): ViewModelFactory<PlayMenuViewModel>

    fun createBucketViewModelFactory(): ViewModelFactory<CreateBucketViewModel>

    fun flashcardViewModelFactory(): ViewModelFactory<FlashcardViewModel>

    fun bucketDetailsViewModelFactory(): ViewModelFactory<BucketDetailsViewModel>

    fun createTemplateViewModelFactory(): ViewModelFactory<CreateTemplateViewModel>
}


