package com.alexkn.syntact.presentation.createbucket

import androidx.lifecycle.*
import androidx.room.Relation
import com.alexkn.syntact.data.model.Phrase
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.domain.repository.TemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import java.util.*
import javax.inject.Inject

class CreateBucketViewModel @Inject
constructor(
        private val bucketRepository: BucketRepository,
        private val templateRepository: TemplateRepository
) : ViewModel() {

    val availableLanguages: MutableList<Locale> = bucketRepository.availableLanguages


    var availableTemplates: LiveData<List<Template>> = templateRepository.findTemplates()


    init {
        viewModelScope.launch {
            templateRepository.updateTemplates()
        }
    }

    fun addBucketFromExistingTemplate(template: Template) {
        GlobalScope.launch(Dispatchers.Default) {
            bucketRepository.addBucketWithExistingTemplate(template)
        }
    }

}
