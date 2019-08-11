package com.alexkn.syntact.presentation.createbucket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.domain.repository.TemplateRepository
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import java.util.*
import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(
        private val bucketRepository: BucketRepository,
        private val templateRepository: TemplateRepository
) : ViewModel() {

    val availableBuckets: MutableList<Locale> = bucketRepository.availableLanguages

    var availableTemplates: LiveData<List<Template>> = templateRepository.findTemplates()

    init {
        viewModelScope.launch {
            templateRepository.updateTemplates()
        }
    }

    fun addBucketFromExistingTemplate(template: Template) {
        viewModelScope.launch {
            bucketRepository.addBucketWithExistingTemplate(template)
        }
    }
}
