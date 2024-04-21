package com.example.mobilesecurity2024a_youtubemp3converter.Class;
import com.google.gson.Gson;

class DownloadResponse {
    private int quality;
    private String startTime;
    private String id;
    private String downloadUrl;
    private String endTime;
    private String status;
    private String startAt;
    private String title;
    private String endAt;
    private String format;


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}

