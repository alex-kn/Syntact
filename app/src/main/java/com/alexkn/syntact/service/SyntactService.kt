package com.alexkn.syntact.service

import retrofit2.http.*

interface SyntactService {

    @GET("templates")
    suspend fun getTemplates(@Header("Authorization") token: String): List<TemplateResponse>

    @GET
    suspend fun getPhrases(
            @Header("Authorization") token: String,
            @Url url: String
    ): List<PhraseResponse>

    @GET("suggestions/sentences")
    suspend fun getSuggestionSentences(
            @Header("Authorization") token: String,
            @Query("phrase") phrase: String,
            @Query("srcLang") srcLang: String,
            @Query("destLang") destLang: String
    ): PhraseSuggestionResponse

    @GET("suggestions/words")
    suspend fun getSuggestionWords(
            @Header("Authorization") token: String,
            @Query("phrase") phrase: String,
            @Query("srcLang") srcLang: String,
            @Query("destLang") destLang: String,
            @Query("volume") volume: Int
    ): PhraseSuggestionResponse

    @POST("templates")
    suspend fun postTemplate(
            @Body template: TemplateRequest
    ): TemplateResponse
}
