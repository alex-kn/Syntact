package com.alexkn.syntact.restservice

import android.util.Log
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.NoSuchElementException

@Singleton
class SolvableItemService @Inject
constructor(private val property: Property, private val syntactService: SyntactService) {

    suspend fun fetchNewTranslations(bucket: Bucket, minFetchId: Long, count: Int): List<SolvableTranslationCto> {

        val token = "Token " + property["api-auth-token"]

        Log.i(TAG, "Fetching $count phrases with ids above $minFetchId")

        val solvableTranslationCtos = ArrayList<SolvableTranslationCto>()

        val phrases = syntactService.getPhrases(token, bucket.phrasesUrl, minFetchId, count)


        return phrases.map { phrase ->
            val solvableItem = SolvableItem(
                    id = phrase.id,
                    text = phrase.text.capitalize(),
                    bucketId = bucket.id
            )
            Log.i(TAG, "Fetched Phrase " + phrase.text + ". Fetching translation...")
            val translations = syntactService.getTranslations(token, phrase.translationsUrl, bucket.userLanguage.language)

            if (translations.size > 1) {
                Log.i(TAG, "Multiple Translations not yet supported, using first translation and discarding others")
            }
            if (translations.isEmpty()) {
                throw NoSuchElementException("No Translation found for $phrase in $bucket")
            }

            val translation = translations[0]
            val clue = Clue(
                    id = translation.id,
                    text = translation.text.capitalize(),
                    solvableItemId = phrase.id
            )
            SolvableTranslationCto(solvableItem = solvableItem, clue = clue)
        }
    }
}
