package com.alexkn.syntact.restservice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SyntactService {

    @GET("phrases/")
    fun getPhrases(@Header("Authorization") token: String): Call<List<Phrase>>

    @GET("templates/")
    suspend fun getTemplates(@Header("Authorization") token: String): List<Template>

    @GET("templates/{id}/phrases/")
    fun getPhrases(@Header("Authorization") token: String, @Path("id") templateId: Long?, @Query("minid") minid: Long,
                   @Query("limit") limit: Int): Call<List<Phrase>>

    @GET("phrases/{id}/translations/")
    fun getTranslations(@Header("Authorization") token: String, @Path("id") phraseId: Long?, @Query("lang") lang: String): Call<List<Translation>>

    @GET
    suspend fun getTranslations(@Header("Authorization") token: String, @Url url: String, @Query("lang") lang: String): List<Translation>

    @GET
    suspend fun getPhrases(@Header("Authorization") token: String, @Url url: String, @Query("minid") minid: Long, @Query("limit") limit: Int): List<Phrase>

    @POST("templates/{id}/phrases/")
    fun postPhrases(@Header("Authorization") token: String, @Path("id") templateId: Long?, @Query("lang") lang: String,
                    @Body phrasesRequests: List<PhrasesRequest>): Call<ResponseBody>

    @POST("templates/")
    fun postTemplate(@Header("Authorization") token: String, @Body templateRequest: TemplateRequest): Call<Template>

    @DELETE("templates/{id}")
    fun deleteTemplate(@Header("Authorization") token: String, @Path("id") templateId: Long?): Call<ResponseBody>
}
