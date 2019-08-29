package com.rahmatharyadi.dicodingtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rahmatharyadi.dicodingtest.adapter.CardViewStartupAdapter;
import com.rahmatharyadi.dicodingtest.model.ModelStartup;
import com.rahmatharyadi.dicodingtest.model.Startup;
import com.rahmatharyadi.dicodingtest.retrofit.APIUtilities;
import com.rahmatharyadi.dicodingtest.retrofit.RequestAPIServices;
import com.rahmatharyadi.dicodingtest.utility.LoadingClass;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Context context = this;

    private RequestAPIServices apiServices;

    @BindView(R.id.rvStartup)
    RecyclerView rvStartup;

    private ArrayList<Startup> listStartups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getAPIStartup();

        rvStartup.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.aboutMe:
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showRecyclerCardView() {
        rvStartup.setLayoutManager(new LinearLayoutManager(this));
        CardViewStartupAdapter cardViewHeroAdapter = new CardViewStartupAdapter(listStartups);
        rvStartup.setAdapter(cardViewHeroAdapter);
    }

    public void getAPIStartup() {
        final ProgressDialog loading = LoadingClass.loadingAnimation(context);
        loading.show();
        apiServices = APIUtilities.getAPIServices();
        apiServices.getStartup("application/json; charset=utf-8").enqueue(new Callback<ModelStartup>() {
            @Override
            public void onResponse(@NonNull Call<ModelStartup> call, @NonNull Response<ModelStartup> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        Startup startup = new Startup();
                        startup.setName(response.body().getData().get(i).getName());
                        startup.setDescription(response.body().getData().get(i).getDescription());
                        startup.setPicture(response.body().getData().get(i).getPicture());
                        startup.setWeb(response.body().getData().get(i).getWeb());
                        System.out.println("Nama : " + response.body().getData().get(i).getName() + " " + i);
                        listStartups.add(startup);
                    }

                    showRecyclerCardView();

                } else {
                    System.out.println("Masuk Response : " + response.code());
                    Log.d("Test", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelStartup> call, @NonNull Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "Login User onFailure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("ONFAILUR DB = " + t.getMessage());
            }
        });
    }

}
