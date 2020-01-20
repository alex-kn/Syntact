package com.alexkn.syntact.service

import okhttp3.Response
import retrofit2.http.*
import java.util.*

interface SyntactService {

    @GET("templates")
    suspend fun getTemplates(@Header("Authorization") token: String): List<TemplateResponse>

    @GET
    suspend fun getPhrases(
            @Header("Authorization") token: String,
            @Url url: String
    ): List<PhraseResponse>

    @GET("suggestions")
    suspend fun getPhraseSuggestions(
            @Header("Authorization") token: String,
            @Query("phrase") phrase: String,
            @Query("srcLang") srcLang: String,
            @Query("destLang") destLang: String
    ): PhraseSuggestionResponse

    @POST("templates")
    suspend fun postTemplate(
            @Body template: TemplateRequest
    ): TemplateResponse
}
