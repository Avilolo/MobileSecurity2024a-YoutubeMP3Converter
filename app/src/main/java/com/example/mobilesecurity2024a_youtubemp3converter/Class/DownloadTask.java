package com.example.mobilesecurity2024a_youtubemp3converter.Class;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.mobilesecurity2024a_youtubemp3converter.MainActivity;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;

public class DownloadTask extends AsyncTask<String, Void, Void> {

    private Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String youtubeUrl = params[0];
//        AsyncHttpClient client = new DefaultAsyncHttpClient();
//        client.prepare("GET", getUrlAux(youtubeUrl))
//                .setHeader("X-RapidAPI-Key", "5d7f9989f9msh27f0ec58c6aae7ap1ca6d7jsn30364c5b5ca8")
//                .setHeader("X-RapidAPI-Host", "youtube-mp3-downloader2.p.rapidapi.com")
//                .execute()
//                .toCompletableFuture()
//                .thenAccept(response -> {
//                    // Parse the JSON response
//                    String responseBody = response.getResponseBody();
//                    JsonParser parser = new JsonParser();
//                    JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
//                    // Extract the downloadUrl
//                    String downloadUrl = jsonObject.get("dlink").getAsString();
//                    Log.d("Download URL", downloadUrl);

        // Create an Intent with ACTION_VIEW to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW);

        String downloadUrl = "https://mymp3.xyz/phmp3?fname=KDiA9dy2fUU128.mp3";

        intent.setData(Uri.parse(downloadUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Check if there's an app available to handle this intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            // Open the URL using the default app
            context.startActivity(intent);
        } else {
            // If no app is available, show a message to the user
//                        Toast.makeText(context, "No app found to open the URL", Toast.LENGTH_SHORT).show();
        }
        try {
//                        client.close();
        } catch (Exception e/*IOException e*/) {
//                        e.printStackTrace();
        }
//                })
//                .join();

        return null;
    }

    private String getUrlAux(String youtubeUrl) {
        int index = youtubeUrl.indexOf('=');
        String url = null;
        if (index != -1) {
            url = "https://youtube-mp3-downloader2.p.rapidapi.com/ytmp3/ytmp3/?url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"
                    + youtubeUrl.substring(index + 1);
        }
        return url;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Show a Toast message on the UI thread
        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
    }
}

