package com.alexkn.syntact.restservice;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SyntactService {

    @GET("phrases")
    Call<List<Phrase>> getPhrases(@Header("Authorization") String token,
            @Query("sourceLang") String sourceLanguageCode,
            @Query("targetLang") String targetLanguageCode, @Query("template") Integer template);

    @GET("templates")
    Call<List<Translation>> getTemplates(@Header("Authorization") String token);

    @POST("templates/")
    Call<List<Translation>> postTemplate(@Header("Authorization") String token,
            @Body Translation templateResponse);

    @POST("translate-template")
    Call<ResponseBody> translateTemplate(@Header("Authorization") String token,
            @Query("targetLang") String targetLanguage, @Query("templateId") Integer templateId);

    @GET("templates/{id}")
    Call<Translation> getTemplate(@Header("Authorization") String token,
            @Path("id") int templateId);
}
