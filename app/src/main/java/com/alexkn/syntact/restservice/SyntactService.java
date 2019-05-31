package com.alexkn.syntact.restservice;

import com.alexkn.syntact.domain.repository.TemplateRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SyntactService {

    @GET("phrases")
    Call<List<PhraseResponse>> getPhrases(@Header("Authorization") String token,
            @Query("sourceLang") String sourceLanguageCode,
            @Query("targetLang") String targetLanguageCode, @Query("template") Integer template);

    @GET("templates")
    Call<List<TemplateResponse>> getTemplates(@Header("Authorization") String token);

    @POST("templates/")
    Call<List<TemplateResponse>>  postTemplate(@Header("Authorization") String token,
            @Body TemplateResponse templateResponse);
}
