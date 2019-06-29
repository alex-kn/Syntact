package com.alexkn.syntact.restservice

import io.reactivex.Maybe
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface SyntactService {

    @GET("phrases/")
    fun getPhrases(@Header("Authorization") token: String): Call<List<Phrase>>

    @GET("templates/")
    fun getTemplates(@Header("Authorization") token: String): Call<List<Template>>

    @GET("templates/{id}/phrases/")
    fun getPhrases(@Header("Authorization") token: String, @Path("id") templateId: Long?, @Query("minid") minid: Long,
                   @Query("limit") limit: Int): Call<List<Phrase>>

    @GET("phrases/{id}/translations/")
    fun getTranslations(@Header("Authorization") token: String, @Path("id") phraseId: Long?, @Query("lang") lang: String): Call<List<Translation>>

    @GET
    fun getTranslations(@Header("Authorization") token: String, @Url url: String, @Query("lang") lang: String): Maybe<List<Translation>>

    @GET
    fun getPhrases(@Header("Authorization") token: String, @Url url: String, @Query("minid") minid: Long, @Query("limit") limit: Int): Maybe<List<Phrase>>

    @POST("templates/{id}/phrases/")
    fun postPhrases(@Header("Authorization") token: String, @Path("id") templateId: Long?, @Query("lang") lang: String,
                    @Body phrasesRequests: List<PhrasesRequest>): Call<ResponseBody>

    @POST("templates/")
    fun postTemplate(@Header("Authorization") token: String, @Body templateRequest: TemplateRequest): Call<Template>

    @DELETE("templates/{id}")
    fun deleteTemplate(@Header("Authorization") token: String, @Path("id") templateId: Long?): Call<ResponseBody>
}
