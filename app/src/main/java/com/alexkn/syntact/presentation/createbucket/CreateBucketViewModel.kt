package com.alexkn.syntact.presentation.createbucket

import android.os.AsyncTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.restservice.Template

import org.apache.commons.lang3.StringUtils

import java.util.Arrays
import java.util.Locale

import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(private val bucketRepository: BucketRepository) : ViewModel() {

    val availableBuckets: MutableList<Locale> = bucketRepository.availableLanguages

    val availableTemplates: LiveData<List<Template>> = bucketRepository.findAvailableTemplates()

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

    fun addBucket(language: Locale, template: Template?) {
        AsyncTask.execute {
            bucketRepository
                    .addBucketWithExistingTemplate(language, template ?: availableTemplates.value!![0])
        }
    }
}
