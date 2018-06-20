package com.dexterlearning.dexapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

public class CourseActivity extends AppCompatActivity {

    WebView webView;
    TextView tvTitle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        //Add custom toolbar to the activity
       // LayoutInflater layoutInf = LayoutInflater.from(CourseActivity.this);
        //View view = layoutInf.inflate(R.layout.custom_toolbar, null, false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //TODO: Set title depending on the course title selected
        String title = getIntent().getStringExtra("title");
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        String url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient(){

            //TODO: Figure out how to differentiate between platforms
            @Override
            @TargetApi(23)
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(CourseActivity.this, "WebView Error" + error.getDescription(),
                        Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, request, error);
            }

            @Override
            @TargetApi(23)
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Toast.makeText(CourseActivity.this, "WebView Error" + errorResponse.getReasonPhrase(),
                        Toast.LENGTH_SHORT).show();
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        if(!isConnected(CourseActivity.this)){
            Toast.makeText(CourseActivity.this, "Your device is offline ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    public static boolean isConnected(Context context){
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cManager != null){
            NetworkInfo info = cManager.getActiveNetworkInfo();

            return(info != null && info.isConnected());
        }
        return false;
    }
}
