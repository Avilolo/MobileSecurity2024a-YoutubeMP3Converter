package com.example.mobilesecurity2024a_youtubemp3converter.Class;
import com.example.mobilesecurity2024a_youtubemp3converter.Class.Interfaces.ConversionCallback;
import com.example.mobilesecurity2024a_youtubemp3converter.Class.Interfaces.ConversionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConversionManager {

//    private static final String XRapidAPIKey = "5d7f9989f9msh27f0ec58c6aae7ap1ca6d7jsn30364c5b5ca8";
    private static final String RAPID_API_HOST = "youtube-to-mp315.p.rapidapi.com";
//    private static final String BASE_URL = "https://ytmp3.nu/Vpy1/";

    private ConversionService conversionService;

    public ConversionManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RAPID_API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        conversionService = retrofit.create(ConversionService.class);
    }

    public void convertToMp3(String videoUrl, final ConversionCallback callback) {
        Call<ConversionResponse> call = conversionService.convertToMp3(videoUrl);
        call.enqueue(new Callback<ConversionResponse>() {
            @Override
            public void onResponse(Call<ConversionResponse> call, Response<ConversionResponse> response) {
                if (response.isSuccessful()) {
                    ConversionResponse conversionResponse = response.body();
                    if (conversionResponse != null) {
                        String mp3Url = conversionResponse.getMp3Url();
                        callback.onSuccess(mp3Url);
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<ConversionResponse> call, Throwable t) {
                callback.onError();
            }
        });
    }
}


