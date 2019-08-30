package com.rahmatharyadi.dicodingtest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageDetailsActivity extends AppCompatActivity {
    private Context context = this;

    @BindView(R.id.imageStartups)
    ImageView imageStartups;
    @BindView(R.id.detailDescriptions)
    TextView detailDescriptions;
    @BindView(R.id.buttonPage)
    Button buttonPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String picture = intent.getStringExtra("picture");
        final String web = intent.getStringExtra("web");

        Glide.with(context)
                .load(picture)
                .apply(new RequestOptions().override(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .into(imageStartups);
        detailDescriptions.setText(description);

        buttonPage.setText("Shopping at "+name+" >");

        buttonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = web;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/UbuntuMono-B.ttf");
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(name);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
