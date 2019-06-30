package com.alexkn.syntact.presentation.createbucket

import android.os.AsyncTask
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexkn.syntact.app.TAG

import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.restservice.Template
import io.reactivex.disposables.CompositeDisposable

import org.apache.commons.lang3.StringUtils

import java.util.Arrays
import java.util.Locale

import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(private val bucketRepository: BucketRepository) : ViewModel() {

    val availableBuckets: MutableList<Locale> = bucketRepository.availableLanguages

    var availableTemplates: MutableLiveData<List<Template>> = MutableLiveData()

    var disp: CompositeDisposable = CompositeDisposable()

    init {
        fetchTemplates()
    }

    override fun onCleared() {
        disp.dispose()
    }

    private fun fetchTemplates() {
        AsyncTask.execute {

            val disposable = bucketRepository.findAvailableTemplates().subscribe(availableTemplates::postValue) { Log.e(TAG, "fetchTemplates: ", it) }
            disp.add(disposable)
        }
    }

    fun addBucket(language: Locale, template: Template?, words: String) {

        if (words.isEmpty()) {
            AsyncTask.execute {
                bucketRepository
                        .addBucketWithExistingTemplate(language, template ?: availableTemplates.value!![0])
            }
        } else {
            val phrases = listOf(*StringUtils.split(words, " "))
            AsyncTask.execute { bucketRepository.addBucketWithNewTemplate("AndroidTest", language, phrases) }
        }
    }

    fun addBucketFromExistingTemplate(template: Template) {
        AsyncTask.execute {
            bucketRepository
                    .addBucketWithExistingTemplate(template.language, template)
        }
    }

    fun addBucketFromNewTemplate(language: Locale, phrases: List<String>) {
        AsyncTask.execute {
            val result = bucketRepository.addBucketWithNewTemplate("TestBucket", language, phrases)
            result.addListener({ fetchTemplates() }, { it?.run() })
        }
    }
}
