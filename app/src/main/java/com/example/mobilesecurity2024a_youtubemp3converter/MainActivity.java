package com.example.mobilesecurity2024a_youtubemp3converter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

//import com.example.mobilesecurity2024a_youtubemp3converter.Class.ConversionManager;

import com.example.mobilesecurity2024a_youtubemp3converter.Class.DownloadTask;
import com.google.android.material.textfield.TextInputEditText;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

// youtube link for test:
// https://www.youtube.com/watch?v=KDiA9dy2fUU
// download url for tests -  https://mymp3.xyz/phmp3?fname=KDiA9dy2fUU128.mp3
public class MainActivity extends AppCompatActivity {
    private TextInputEditText url_textInput;
    private AppCompatButton convert_btn;
//    private ConversionManager conversionManager;
    private String title;
    private YouTubeExtractor youtubeExtractor;
    private DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();


//        conversionManager = new ConversionManager();


        convertBtnClicked();


    }

    private void convertBtnClicked() {
        convert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url_textInput.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Url is empty", Toast.LENGTH_LONG).show();
                }
                else if (!url_textInput.getText().toString().contains("https://www.youtube.com/watch?v=")) {
                    Toast.makeText(MainActivity.this, "Invalid url", Toast.LENGTH_LONG).show();
                }
                else {
                    downloadTask = new DownloadTask(getApplicationContext());
                    downloadTask.execute(url_textInput.getText().toString());
                }
            }
        });
    }


//    private void convertBtnClicked() {
//        convert_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            @SuppressLint("StaticFieldLeak")
//            public void onClick(View view) {
//                new YouTubeExtractor(MainActivity.this) {
//                    @Override
//                    protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
//                        if (ytFiles == null) {
//                            Toast.makeText(MainActivity.this, "There was an error while extracting", Toast.LENGTH_SHORT);
//                            return;
//                        }
//                        title = MessageFormat.format("Title: {0}", videoMeta.getTitle());
//                        SparseArray<YtFile> array = new SparseArray<>();
//
//                        YtFile ytFile = null;
//                        for (int i = 0; i < ytFiles.size(); i++) {
//                            ytFile = ytFiles.get(ytFiles.keyAt(i));
//                            if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
//                                array.put(ytFiles.keyAt(i), ytFile);
//                            }
//                        }
//                        String filename = title + "." + ytFile.getFormat().getExt();
//                        filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
//
//                        download(ytFile.getUrl(), videoMeta.getTitle(), filename);
//                    }
//                }.extract(Objects.requireNonNull(url_textInput.getText()).toString(), false, false);
//
////                sendURL();
//            }
//        });
//    }


//    private void sendURL(String fileName) {
//        // Get the YouTube link from TextInputEditText
//        String youtubeLink = "http://youtube.com/watch?v=xxxx";
//
//        download(youtubeLink, fileName);
//
//
////        new YouTubeExtractor(this) {
////            @Override
////            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
////                if (ytFiles != null) {
////                    int itag = 22;
////                    String downloadUrl = ytFiles.get(itag).getUrl();
////                }
////            }
////        }.extract(youtubeLink);
//
////
////        // Call convertToMp3 method with the YouTube link
////        conversionManager.convertToMp3(youtubeLink, new ConversionCallback() {
////            @Override
////            public void onSuccess(String mp3Url) {
////                downloadFile(youtubeLink);
////            }
////
////            @Override
////            public void onError() {
////                Log.d("ptt", "onError: failed");
////            }
////        });
//    }

    public void download(String youtubeUrl) throws IOException {
        Uri uri = Uri.parse(youtubeUrl);
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", youtubeUrl)
                .setHeader("X-RapidAPI-Key", "5d7f9989f9msh27f0ec58c6aae7ap1ca6d7jsn30364c5b5ca8")
                .setHeader("X-RapidAPI-Host", "youtube-mp3-downloader2.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    // Handle the response here
                    System.out.println(response);
                    // You can process the response or perform other actions
                })
                .exceptionally(ex -> {
                    // Handle exceptions here
                    ex.printStackTrace();
                    return null;
                });
        client.close();
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
        grantPermissions();
    }

    //TODO for debugging purposes
    private void grantPermissions() {
        // Access shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("permissions", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set permissions as granted
        editor.putBoolean("INTERNET_permission_granted", true);
        editor.putBoolean("WRITE_EXTERNAL_STORAGE_permission_granted", true);

        // Apply changes
        editor.apply();
    }
}