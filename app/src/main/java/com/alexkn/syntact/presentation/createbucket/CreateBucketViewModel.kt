package com.alexkn.syntact.presentation.createbucket

import android.util.Log
import androidx.lifecycle.*
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.core.repository.BucketRepository
import com.alexkn.syntact.core.repository.TemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(
        private val bucketRepository: BucketRepository,
        private val templateRepository: TemplateRepository
) : ViewModel() {

    var availableLanguages: List<Locale> = bucketRepository.availableLanguages.filter { locale -> locale.language != Locale.getDefault().language }


    var availableTemplates: LiveData<List<Template>> = templateRepository.findTemplates()


    init {
        viewModelScope.launch {
            try {
                templateRepository.updateTemplates()
            } catch (e: Exception) {
                Log.e(TAG, "Update templates: ", e)
            }
        }
    }

    fun addBucketFromExistingTemplate(template: Template) {
        GlobalScope.launch(Dispatchers.Default) {
            bucketRepository.addBucketWithExistingTemplate(template)
        }
    }

}
