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
import retrofit2.http.Url;

public interface SyntactService {

    @GET("phrases/")
    Call<List<Phrase>> getPhrases(@Header("Authorization") String token);

    @GET("templates/")
    Call<List<Template>> getTemplates(@Header("Authorization") String token);

    @GET("templates/{id}/phrases/")
    Call<List<Phrase>> getPhrases(@Header("Authorization") String token, @Path("id") Long templateId, @Query("minid") long minid,
            @Query("limit") int limit);

    @GET("phrases/{id}/translations/")
    Call<List<Translation>> getTranslations(@Header("Authorization") String token, @Path("id") Long phraseId, @Query("lang") String lang);

    @GET
    Call<List<Translation>> getTranslations(@Header("Authorization") String token, @Url String url, @Query("lang") String lang);

    @GET
    Call<List<Phrase>> getPhrases(@Header("Authorization") String token, @Url String url, @Query("minid") long minid, @Query("limit") int limit);

    @POST("templates/{id}/phrases/")
    Call<ResponseBody> postPhrases(@Header("Authorization") String token, @Path("id") Long templateId, @Body List<PhrasesRequest> phrasesRequests);

    @POST("templates/")
    Call<Template> postTemplate(@Header("Authorization") String token, @Body TemplateRequest templateRequest);
}
