package com.example.mobilesecurity2024a_youtubemp3converter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mobilesecurity2024a_youtubemp3converter.Class.ConversionManager;
import com.example.mobilesecurity2024a_youtubemp3converter.Class.Interfaces.ConversionCallback;
import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText url_textInput;
    private AppCompatButton convert_btn;
    private ConversionManager conversionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        conversionManager = new ConversionManager();


        convertBtnClicked();


    }

    private void convertBtnClicked() {
        convert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url_textInput.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Url is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    sendURL();
                }
            }
        });
    }

    private void sendURL() {
        // Get the YouTube link from TextInputEditText
        String youtubeLink = url_textInput.getText().toString();

        // Call convertToMp3 method with the YouTube link
        conversionManager.convertToMp3(youtubeLink, new ConversionCallback() {
            @Override
            public void onSuccess(String mp3Url) {
                downloadFile(youtubeLink);
            }

            @Override
            public void onError() {
                Log.d("ptt", "onError: failed");
            }
        });
    }

    private void downloadFile(String mp3Url) {
        // Create a DownloadManager.Request with the URL of the file to download
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mp3Url));

        // Set the title of the download notification
        request.setTitle("Downloading MP3");

        // Set the description of the download notification
        request.setDescription("Please wait...");

        // Set the destination directory for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "converted_audio.mp3");

        // Get the DownloadManager system service
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // Enqueue the download request
        long downloadId = downloadManager.enqueue(request);
    }

    private void findViews() {
        url_textInput = findViewById(R.id.url_textInput);
        convert_btn = findViewById(R.id.convert_btn);

    }
}