package com.rahmatharyadi.dicodingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageDetailsActivity extends AppCompatActivity {
    private Context context = this;

    @BindView(R.id.detailNames)
    TextView detailNames;
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

        detailNames.setText(name);
        Glide.with(context)
                .load(picture)
                .apply(new RequestOptions().override(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .into(imageStartups);
        detailDescriptions.setText(description);

        buttonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = web;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

}
