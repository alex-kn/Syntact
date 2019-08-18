package com.alexkn.syntact.domain.repository

import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.TemplateDao
import com.alexkn.syntact.data.model.Phrase
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.rest.service.SyntactService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplateRepository @Inject constructor(
        private val property: Property,
        private val syntactService: SyntactService,
        private val templateDao: TemplateDao,
        private val bucketDao: BucketDao
) {

    fun findTemplates(): LiveData<List<Template>> {
        return templateDao.findAvailable()
    }

    suspend fun updateTemplates() {
        val token = "Token " + property["api-auth-token"]
        val templateResponses = syntactService.getTemplates(token)
        val templates = templateResponses.filter {
            it.language.language != Locale.getDefault().language
        }.map {
            Template(
                    id = it.id,
                    name = it.name,
                    templateType = it.templateType,
                    language = it.language,
                    phrasesUrl = it.phrasesUrl,
                    count = it.count,
                    description = it.description
            )
        }
        templateDao.insert(templates)
        templates.forEach {
            val phraseResponses = syntactService.getPhrases(token, it.phrasesUrl)
            val phrases = phraseResponses.map { phrase ->
                Phrase(id = phrase.id, text = phrase.text, templateId = it.id)
            }
            templateDao.insertPhrases(phrases)
        }
    }
}