package com.example.mobilesecurity2024a_youtubemp3converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

//import com.example.mobilesecurity2024a_youtubemp3converter.Class.ConversionManager;

import com.example.mobilesecurity2024a_youtubemp3converter.Class.DownloadTask;
import com.google.android.material.textfield.TextInputEditText;



// youtube link for test:
// https://www.youtube.com/watch?v=KDiA9dy2fUU
// download url for tests -  https://mymp3.xyz/phmp3?fname=KDiA9dy2fUU128.mp3

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 122;
    private TextInputEditText url_textInput;
    private AppCompatButton convert_btn;
//    private ConversionManager conversionManager;
    private String title;
    private DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        showRationaleDialog();
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
                else if (checkPermissions()) {
                    downloadTask = new DownloadTask(getApplicationContext());
                    downloadTask.execute(url_textInput.getText().toString());
                } else {
                    requestPermissions();
                }
            }
        });
    }

    private void findViews() {
        url_textInput = findViewById(R.id.url_textInput);
        convert_btn = findViewById(R.id.convert_btn);
    }

    private void startDownload() {
        if (checkPermissions()) {
            String url = url_textInput.getText().toString();
            new DownloadTask(MainActivity.this).execute(url);
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            // Explain to the user why these permissions are needed
            showRationaleDialog();
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET},
                    PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This permission is needed to download files from YouTube.")
                .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

//    private void requestPermissions() {
//        Log.d("Permissions", "Requesting permissions...");
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET},
//                PERMISSIONS_REQUEST_CODE);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Permissions", "onRequestPermissionsResult: requestCode=" + requestCode);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            Log.d("Permissions", "Permission response received");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Permissions granted");
                startDownload();
            } else {
                Log.d("Permissions", "Permissions not granted");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                    Log.d("Permissions", "Permission denied permanently");
                    Toast.makeText(this, "Permission Denied permanently. Please enable from settings.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Permissions", "Permission denied temporarily");
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



}


