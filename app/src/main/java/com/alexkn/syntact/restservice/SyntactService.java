package com.alexkn.syntact.restservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SyntactService {

    @GET("phrases")
    Call<List<PhraseResponse>> getPhrases(@Header("Authorization") String token,
            @Query("lang") String languageCode);
}
