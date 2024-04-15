package com.example.mobilesecurity2024a_youtubemp3converter.Class.Interfaces;
import com.example.mobilesecurity2024a_youtubemp3converter.Class.ConversionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ConversionService {

    @GET("convert")
    Call<ConversionResponse> convertToMp3(@Query("video_url") String videoUrl);
}

