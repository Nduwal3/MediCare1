package edu.sfsu.cs.orange.ocr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private WebView mwebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//webview
        String url = "http://time.com/time-health/";
        mwebView = (WebView) findViewById(R.id.myWebView);
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //improve webview performance
        mwebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mwebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mwebView.getSettings().setAppCacheEnabled(true);
        mwebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setEnableSmoothTransition(true);
        mwebView.loadUrl("https://www.drugs.com/symptom-checker/");
//force links to open in same webview
        mwebView.setWebViewClient(new MyWebviewClient());


        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirst = getPrefs.getBoolean("firstStart",true);
              //  boolean isLast = getPrefs.getBoolean("fStart",false);

                if(isFirst)
                {
                    startActivity(new Intent(MainActivity.this,MyIntro.class));
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart",false);
                    e.apply();

                }

            }
        });
        thread.start();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.tab_hospital:
                        Toast.makeText(MainActivity.this,"home Clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.tab_health:
                        startActivity(new Intent(MainActivity.this, directory.class));
                        break;

                    case R.id.tab_ocr:
                       // Toast.makeText(MainActivity.this,"Ocr Clicked",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                        break;

                    //case R.id.tab_cloud:
                      //  Toast.makeText(MainActivity.this,"Cloud Clicked",Toast.LENGTH_SHORT).show();
                    // break;

                    case R.id.tab_note:
                        startActivity(new Intent(MainActivity.this, reminder.class));
                        break;
                }
                return true;
            }
        });






      /* bottomBar=(BottomBar) findViewById(R.id.bottombar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            android.app.Fragment fragment = null;
            android.support.v4.app.Fragment fragmen;
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_hospital){
                   fragment = new HospitalFragment();

                }else if (tabId == R.id.tab_health){
                    fragment = new HealthFragment();

                }else if (tabId == R.id.tab_ocr){
                    fragment = new OcrFragment();

                } else if (tabId == R.id.tab_cloud){
                    fragment = new CloudFragment();

                }else if (tabId == R.id.tab_note){
                    fragment = new NoteFragment();

                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,fragmen)
                        .commit();
            }
        });   */
    }


    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }



        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('hhlogo-symptom')[0].style.display = 'none'; "+
                    "document.getElementsByTagName('header')[0].style.display = 'none'; "+
                    "document.getElementsByClassName('footer')[0].style.display = 'none' ;"+
                    "document.getElementsByClassName('footer-feature')[0].style.display = 'none';"+
                    "})()");
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
           switch (keyCode){
               case KeyEvent.KEYCODE_BACK:
               if (mwebView.canGoBack()){
                   mwebView.goBack();
               }
               else {
                   finish();
               }
           }

        }
        return super.onKeyDown(keyCode, event);
    }
}
