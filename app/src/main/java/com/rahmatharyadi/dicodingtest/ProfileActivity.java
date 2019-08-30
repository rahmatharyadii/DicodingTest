package com.rahmatharyadi.dicodingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    private Context context = this;

    @BindView(R.id.myTVMarqueen)
    TextView myTVMarqueen;
    @BindView(R.id.myName)
    TextView myName;
    @BindView(R.id.myEmail)
    TextView myEmail;
    @BindView(R.id.buttonWA)
    TextView buttonWA;
    @BindView(R.id.photoProfile)
    ImageView photoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        myTVMarqueen.setSelected(true);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Glide.with(context)
                .load(R.mipmap.myprofile)
                .into(photoProfile);

        myName.setText(R.string.name);
        myEmail.setText(R.string.email);

        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.linkedin.com/in/rahmatharyadi/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.instagram.com/rahmatharyadii/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        myEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "rahmat.haryadi95@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "We have a job for you.");
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });

        buttonWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=6281221560095&text=Hello";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        //doNothing;
    }

}