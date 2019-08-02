package com.alexkn.syntact.presentation.createbucket

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.restservice.Template
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.mutable.Mutable
import java.util.*
import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(private val bucketRepository: BucketRepository) : ViewModel() {

    val availableBuckets: MutableList<Locale> = bucketRepository.availableLanguages

    var availableTemplates: MutableLiveData<List<Template>> = MutableLiveData()

    init {
        availableTemplates.value = listOf()
        fetchTemplates()
    }


    private fun fetchTemplates() {
        viewModelScope.launch {
            var list = bucketRepository.findAvailableTemplates()
            list = list.filter { template ->
                template.language.language != Locale.getDefault().language
            }
            availableTemplates.postValue(list)
            Log.i(TAG, list.size.toString() + " Template(s) found")
        }


    }

    fun addBucket(language: Locale, template: Template?, words: String) {

        if (words.isEmpty()) {
            viewModelScope.launch {
                bucketRepository
                        .addBucketWithExistingTemplate(template ?: availableTemplates.value!![0])
            }
        } else {
            val phrases = listOf(*StringUtils.split(words, " "))
            bucketRepository.addBucketWithNewTemplate("AndroidTest", language, phrases)
        }

    }

    fun addBucketFromExistingTemplate(template: Template) {
        viewModelScope.launch {
            bucketRepository
                    .addBucketWithExistingTemplate(template)
        }
    }

    fun addBucketFromNewTemplate(language: Locale, phrases: List<String>) {
        viewModelScope.launch {
            val result = bucketRepository.addBucketWithNewTemplate("TestBucket", language, phrases)
            result.addListener({
                fetchTemplates()
            }, { Handler(Looper.getMainLooper()).postDelayed(it, 1000) }
            )
        }
    }
}
