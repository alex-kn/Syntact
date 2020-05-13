package dev.alexknittel.syntact.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SyntactService {

    @GET
    suspend fun fetch(@Url url: String, @Query("source") srcLang: String, @Query("query") phrase: String): Response<String>
}
