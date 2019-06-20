package com.alexkn.syntact.restservice

import android.util.Log
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import java.io.IOException
import java.time.Instant
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolvableItemService @Inject
constructor(private val property: Property, private val syntactService: SyntactService) {

    fun fetchNewTranslations(bucket: Bucket, minFetchId: Long, count: Int): List<SolvableTranslationCto> {

        val token = "Token " + property["api-auth-token"]

        Log.i(TAG, "Fetching $count phrases with ids above $minFetchId")

        val solvableTranslationCtos = ArrayList<SolvableTranslationCto>()

        try {
            val phraseResponse = syntactService.getPhrases(token, bucket.phrasesUrl, minFetchId, count).execute()

            if (phraseResponse.isSuccessful) {
                val phrases = phraseResponse.body()
                if (phrases != null) {
                    Log.i(TAG, phrases.size.toString() + " phrases fetched")
                    for (phrase in phrases) {

                        val solvableItem = SolvableItem(
                                id = phrase.id,
                                text = phrase.text,
                                bucketId = bucket.id
                        )

                        Log.i(TAG, "Fetched Phrase " + phrase.text + ". Fetching translation...")

                        val translationResponse = syntactService
                                .getTranslations(token, phrase.translationsUrl, bucket.userLanguage.language).execute()

                        if (translationResponse.isSuccessful && translationResponse.body() != null) {
                            if (translationResponse.body()!!.size == 0) {
                                Log.i(TAG, "No Translation to " + bucket.userLanguage.displayLanguage + " for Phrase " + phrase.text +
                                        " found")
                            } else {
                                if (translationResponse.body()!!.size > 1) {
                                    Log.i(TAG, "Multiple Translations not yet supported, using first translation and discard others")
                                }
                                val translation = translationResponse.body()!![0]

                                val clue = Clue(
                                        id = translation.id,
                                        text = translation.text,
                                        solvableItemId = phrase.id
                                )
                                val solvableTranslationCto = SolvableTranslationCto(solvableItem = solvableItem, clue = clue)
                                solvableTranslationCtos.add(solvableTranslationCto)
                                Log.i(TAG, "Fetched Translation for Phrase " + phrase.text)
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return solvableTranslationCtos
    }

    companion object {

        private val TAG = SolvableItemService::class.java.simpleName
    }
}
