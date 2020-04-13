package com.alexkn.syntact.service

import android.util.Log
import com.alexkn.syntact.app.TAG
import java.util.*

class SyntactServiceMock : SyntactService {

    override suspend fun getSuggestionSentences(token: String, phrase: String, srcLang: String, destLang: String): PhraseSuggestionResponse {
        Log.i(TAG, "SyntactServiceMock: Returning mocked Sentences")

        return PhraseSuggestionResponse(listOf(
                Suggestion(id = 1, src = "Das ist ein deutscher Satz", dest = "This is an english sentence", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH)
        ))
    }

}