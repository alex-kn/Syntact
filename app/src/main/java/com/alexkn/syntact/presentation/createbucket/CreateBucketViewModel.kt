package com.alexkn.syntact.presentation.createbucket

import android.os.AsyncTask
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alexkn.syntact.app.TAG

import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.restservice.Template

import org.apache.commons.lang3.StringUtils
import java.security.SecureRandom

import java.util.Arrays
import java.util.Locale

import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(private val bucketRepository: BucketRepository) : ViewModel() {

    val availableBuckets: MutableList<Locale> = bucketRepository.availableLanguages

    val availableTemplates: LiveData<List<Template>> = bucketRepository.findAvailableTemplates()

    lateinit var selectedTemplate: Template
    lateinit var selectedLanguage: Locale

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

    fun addBucket() {
        Log.i(TAG, "Language " + selectedLanguage.displayLanguage)
        Log.i(TAG, "Template " + selectedTemplate.id + selectedTemplate.name)
        AsyncTask.execute {

            bucketRepository.addBucketWithExistingTemplate(selectedLanguage, selectedTemplate)
        }
    }
}
