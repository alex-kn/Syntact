package com.alexkn.syntact.service

import com.alexkn.syntact.service.to.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SyntactService{

    @GET("templates")
    suspend fun getTemplates(@Header("Authorization") token: String): List<TemplateResponse>

    @GET
    suspend fun getTranslations(@Header("Authorization") token: String, @Url url: String, @Query("lang") lang: String): List<TranslationResponse>

    @GET
    suspend fun getPhrases(@Header("Authorization") token: String, @Url url: String): List<PhraseResponse>

    @POST("templates/{id}/phrases")
    fun postPhrases(@Header("Authorization") token: String, @Path("id") templateId: Long?, @Query("lang") lang: String,
                    @Body phrasesRequests: List<PhrasesRequest>): Call<ResponseBody>

    @POST("templates")
    suspend fun postTemplate(@Header("Authorization") token: String, @Body templateRequest: TemplateRequest): ResponseBody

    @DELETE("templates/{id}")
    fun deleteTemplate(@Header("Authorization") token: String, @Path("id") templateId: Long?): Call<ResponseBody>
}
