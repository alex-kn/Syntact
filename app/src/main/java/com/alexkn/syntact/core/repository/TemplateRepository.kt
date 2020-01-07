package com.alexkn.syntact.core.repository

import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.AuthProv
import com.alexkn.syntact.data.dao.TemplateDao
import com.alexkn.syntact.data.model.Phrase
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.service.PhraseRequest
import com.alexkn.syntact.service.PhraseSuggestionResponse
import com.alexkn.syntact.service.SyntactService
import com.alexkn.syntact.service.TemplateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplateRepository @Inject constructor(
        private val syntactService: SyntactService,
        private val templateDao: TemplateDao,
        private val authenticationProvider: AuthProv
) {

    fun findTemplates(): LiveData<List<Template>> {
        return templateDao.findAvailable()
    }

    suspend fun updateTemplates() = withContext(Dispatchers.Default) {
        val token = authenticationProvider.requestToken()
        val templateResponses = syntactService.getTemplates("Bearer $token")
        val templates = templateResponses.filter {
            it.language.language != Locale.getDefault().language
        }.map {
            Template(
                    id = it.id,
                    name = it.name,
                    templateType = it.templateType,
                    language = it.language,
                    phrasesUrl = it.phrasesUrl,
                    count = 0,
                    description = it.description
            )
        }
        templates.forEach {
            launch {

                val phraseResponses = syntactService.getPhrases("Bearer $token", it.phrasesUrl)
                it.count = phraseResponses.size
                templateDao.insert(it)
                val phrases = phraseResponses.map { phrase ->
                    Phrase(id = phrase.id, text = phrase.text, templateId = it.id)
                }
                templateDao.insertPhrases(phrases)
            }
        }
        templateDao.deleteTemplatesNotIn(templates.map { it.id })
    }

    suspend fun createNewTemplate(suggestions: List<PhraseSuggestionResponse>) {
        val templateRequest = TemplateRequest(suggestions.map {
            PhraseRequest(src = it.src, dest = it.dest, srcLang = it.srcLang, destLang = it.destLang)
        })
        syntactService.postTemplate(templateRequest)
    }
}