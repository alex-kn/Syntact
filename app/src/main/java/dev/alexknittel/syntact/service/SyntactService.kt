package dev.alexknittel.syntact.service

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SyntactService {

    @GET("suggestions/sentences")
    suspend fun getSuggestionSentences(
            @Header("Authorization") token: String,
            @Query("phrase") phrase: String,
            @Query("srcLang") srcLang: String,
            @Query("destLang") destLang: String
    ): PhraseSuggestionResponse

}
