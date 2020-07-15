package com.example.newsapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsapp.Adapter.Utils;
import com.example.newsapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NewsDetailActivity extends AppCompatActivity {



    ImageView img;
    TextView title_on_appbar,subtitle_on_appbar,title,time,date;
    boolean isHideTolbarView = false;
    FrameLayout date_behavior;
    LinearLayout title_appbar;
    AppBarLayout appbar;
    String mUrl,mImg,mTitle,mDate,mSource,mAuthor;

    androidx.appcompat.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        date_behavior = findViewById(R.id.date_behavior);
        title_appbar = findViewById(R.id.title_appbar);
        img = findViewById(R.id.backdrop);
        title_on_appbar = findViewById(R.id.title_on_appbar);
        subtitle_on_appbar = findViewById(R.id.subtitle_on_appbar);
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appbar = findViewById(R.id.appbar);
        appbar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffSet) {
                int mScroll = appBarLayout.getTotalScrollRange();
                float  Percentage = (float)Math.abs(verticalOffSet) / (float) mScroll;

                if(Percentage == 1 && isHideTolbarView){
                    date_behavior.setVisibility(View.GONE);
                    title_appbar.setVisibility(View.VISIBLE);
//                    title_on_appbar.setVisibility(View.GONE);
//                    subtitle_on_appbar.setVisibility(View.GONE);
                    isHideTolbarView = !isHideTolbarView;
                }else {
                    date_behavior.setVisibility(View.VISIBLE);
                    title_appbar.setVisibility(View.GONE);
//                    title_on_appbar.setVisibility(View.VISIBLE);
//                    subtitle_on_appbar.setVisibility(View.VISIBLE);
                    isHideTolbarView = !isHideTolbarView;
                }
            }
        });



        // Get data from Recyler VIew
        mUrl = getIntent().getStringExtra("url");
        mImg = getIntent().getStringExtra("img");
        mTitle = getIntent().getStringExtra("title");
        mDate = getIntent().getStringExtra("date");
        mSource = getIntent().getStringExtra("source");
        mAuthor = getIntent().getStringExtra("mAuthor");



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        requestOptions.timeout(3000);
        Glide.with(this)
                .load(mImg)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img);



        title_on_appbar.setText(mTitle);
        subtitle_on_appbar.setText(mUrl);
        title.setText(mTitle);
        date.setText(mDate);

        String author = null;
        if (mAuthor != "" || mAuthor !=null)
        {
            author = " \u2022 " + mAuthor;
        }
        else {
            author =" ";
        }

        time.setText(mSource + author+" \u2022 " + Utils.DateToTimeFormat(mDate));

        initalWebView(mUrl);







    }

    private void initalWebView(String Url){
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Url);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_news_detail,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.news_share){
            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plan");
                intent.putExtra(Intent.EXTRA_SUBJECT,mSource);
                String body = mTitle + "\n" + "Share from News App"+"\n";
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent,"Share with :"));
            } catch (Exception e) {
                Toast.makeText(this, "Sorry...\nCan not Share ", Toast.LENGTH_SHORT).show();
            }

        }else if (id == R.id.view_web){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mUrl));
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
