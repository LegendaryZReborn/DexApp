package com.dexterlearning.dexapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FileUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;


public class CourseActivity extends AppCompatActivity {
    private TextView tvTitle;
    private Toolbar toolbar;
    private PDFView pdfView;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("title");
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        String pdfFileName = getIntent().getStringExtra("file");
        pdfView = (PDFView) findViewById(R.id.pdfView);
        loadCoursePdf(pdfFileName);
    }

    private void loadCoursePdf(String pdfFileName) {
        mStorageRef.child("courses/" + pdfFileName).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                File localFile = null;
                // Use the bytes to display the image
                try {
                    localFile = File.createTempFile("temp", ".pdf");
                    String absPath = localFile.getAbsolutePath();
                    com.google.common.io.Files.write(bytes, localFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(localFile != null) {
                    pdfView.fromFile(localFile)
                            .enableSwipe(true) // allows to block changing pages using swipe
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                            .password(null)
                            .scrollHandle(null)
                            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                            // spacing between pages in dp. To define spacing color, set view background
                            .spacing(0)
                            .load();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //TODO:HANDLE THIS
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(CourseActivity.this,
                        com.dexterlearning.dexapp.DashboardActivity.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CourseActivity.this,
                com.dexterlearning.dexapp.DashboardActivity.class);
        intent.putExtra("user", getIntent().getStringExtra("user"));
        startActivity(intent);
        finish();
    }

/*
    public static boolean isConnected(Context context){
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cManager != null){
            NetworkInfo info = cManager.getActiveNetworkInfo();

            return(info != null && info.isConnected());
        }
        return false;
    }
    */
}
