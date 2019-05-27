package com.alexkn.syntact.restservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SyntactService {

    @GET("phrases")
    Call<List<PhraseResponse>> getPhrases(@Header("Authorization") String token,
            @Query("sourceLang") String sourceLanguageCode,
            @Query("targetLang") String targetLanguageCode, @Query("template") Integer template);

    @GET("templates")
    Call<List<TemplateResponse>> getTemplates(@Header("Authorization") String token);

}
