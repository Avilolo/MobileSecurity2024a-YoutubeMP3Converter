package com.example.mobilesecurity2024a_youtubemp3converter.Class;

import com.google.gson.annotations.SerializedName;

public class ConversionResponse {

    @SerializedName("mp3_url")
    private String mp3Url;

    public String getMp3Url() {
        return mp3Url;
    }
}

