package vsmu.testing.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.Utils;

/**
 * Created by Dan on 08.04.2016.
 */
public class LearningWebActivity extends Activity {

    final public static String POSITION = "position";

    private WebView mWebView;
    private ImageView back, forward;
    private int green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        final int position = getIntent().getIntExtra(POSITION, App.get().getDisciplinePosition());
        mWebView = findViewById(R.id.webView);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);
        ((TextView) findViewById(R.id.toolbar_title)).setText(getResources().getStringArray(R.array.disсiplines)[position]);
        green = ContextCompat.getColor(this, R.color.green);
        ((ImageView) findViewById(R.id.navigationIcon)).setColorFilter(green);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.loadUrl(getResources().getStringArray(R.array.learningMaterialURL)[position]);

        findViewById(R.id.navigationIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_left, R.anim.out_right);
            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.share(LearningWebActivity.this, mWebView.getUrl());
            }
        });

        findViewById(R.id.open_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl())));
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.contains(".pdf") || url.contains(".doc") || url.contains(".docx") || url.contains(".xlsx")) {
                        view.loadUrl("https://docs.google.com/viewer?url=" + url);
                    } else {
                        view.loadUrl(url);
                    }
                } catch (Exception e) {
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                changeColor();
            }

        });

        back.setColorFilter(Color.GRAY);
        forward.setColorFilter(Color.GRAY);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
            }
        });
    }

    private void changeColor(){
        if (mWebView.canGoBack()) {
            back.setColorFilter(green);
        }else{
            back.setColorFilter(Color.GRAY);
        }
        if (mWebView.canGoForward()) {
            forward.setColorFilter(green);
        }else{
            forward.setColorFilter(Color.GRAY);
        }
    }

    @Override
    public void onBackPressed() {
        changeColor();
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_left, R.anim.out_right);
        }
    }
}