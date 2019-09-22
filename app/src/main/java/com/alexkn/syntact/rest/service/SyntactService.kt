package com.alexkn.syntact.rest.service

import com.alexkn.syntact.rest.to.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SyntactService {

    @GET("templates")
    suspend fun getTemplates(@Header("Authorization") token: String): List<TemplateResponse>

    @GET
    suspend fun getTranslations(@Header("Authorization") token: String, @Url url: String, @Query("lang") lang: String): List<TranslationResponse>

    @GET
    suspend fun getPhrases(@Header("Authorization") token: String, @Url url: String): List<PhraseResponse>

    @POST("templates/{id}/phrases/")
    fun postPhrases(@Header("Authorization") token: String, @Path("id") templateId: Long?, @Query("lang") lang: String,
                    @Body phrasesRequests: List<PhrasesRequest>): Call<ResponseBody>

    @POST("templates/")
    fun postTemplate(@Header("Authorization") token: String, @Body templateRequest: TemplateRequest): Call<TemplateResponse>

    @DELETE("templates/{id}")
    fun deleteTemplate(@Header("Authorization") token: String, @Path("id") templateId: Long?): Call<ResponseBody>
}
