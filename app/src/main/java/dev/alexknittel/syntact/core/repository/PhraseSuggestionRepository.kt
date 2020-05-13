package dev.alexknittel.syntact.core.repository

import dev.alexknittel.syntact.service.Suggestion
import dev.alexknittel.syntact.service.SyntactService
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhraseSuggestionRepository @Inject constructor(
        private val syntactService: SyntactService
) {

    suspend fun fetchSuggestions(text: String, srcLang: Locale, destLang: Locale) = coroutineScope {

        val srcQuery = srcLang.displayLanguage.toLowerCase(srcLang)
        val destQuery = destLang.displayLanguage.toLowerCase(destLang)

        val url = "https://www.linguee.com/$srcQuery-$destQuery/search"

        val fetchedHtml = syntactService.fetch(url, srcQuery, text).body()
        val parsedHtml = Jsoup.parse(fetchedHtml)

        val cl = if (Locale.ENGLISH == srcLang) "isMainTerm" else "isForeignTerm"

        parsedHtml.select("div.$cl").select("div.example.line").map {
            val src = it.select("span.tag_s")[0].text()
            val dest = it.select("span.tag_t")[0].text()
            Suggestion(src = src, dest = dest, srcLang = srcLang, destLang = destLang)
        }
    }

}
