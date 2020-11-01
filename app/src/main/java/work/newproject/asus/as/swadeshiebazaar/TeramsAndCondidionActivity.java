package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeramsAndCondidionActivity extends AppCompatActivity {




    private WebView webView;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terams_and_condidion);

        ButterKnife.bind(this);
        /*  getCondition();*/
        imgBack.setOnClickListener(v -> onBackPressed());


        webView = (WebView) findViewById(R.id.webview);


        setWebView();

    }
    private void setWebView() {
        webView.loadUrl("http://swadeshiebazaar.com/Website/term");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideProgress();
                } else {

                    showProgress();
                }
            }
        });
    }


    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }


    private void hideProgress() {
        progress_circular.setVisibility(View.INVISIBLE);
    }
}