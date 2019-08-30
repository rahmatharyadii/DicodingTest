package com.rahmatharyadi.dicodingtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rahmatharyadi.dicodingtest.adapter.CardViewStartupAdapter;
import com.rahmatharyadi.dicodingtest.model.ModelStartup;
import com.rahmatharyadi.dicodingtest.model.Startup;
import com.rahmatharyadi.dicodingtest.retrofit.APIUtilities;
import com.rahmatharyadi.dicodingtest.retrofit.RequestAPIServices;
import com.rahmatharyadi.dicodingtest.utility.Constanta;
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
    boolean doubleBackToExitPressedOnce = false;

    private int done = 0;

    @BindView(R.id.rvStartup)
    RecyclerView rvStartup;


    private BroadcastReceiver mNetworkDetectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            checkInternetConnection();
        }
    };

    private AlertDialog mInternetDialog;
    private boolean isConnected;


    private ArrayList<Startup> listStartups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getAPIStartup();
        registerReceiver(mNetworkDetectReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/UbuntuMono-B.ttf");
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(getString(R.string.listStartup));
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);

        rvStartup.setHasFixedSize(true);

        isConnected = false;
        registerReceiver(mNetworkDetectReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

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
                    done = 1;
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
                    Log.d("Test", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelStartup> call, @NonNull Throwable t) {
                loading.dismiss();
//                Toast.makeText(context, "onFailure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Check Your Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {

        unregisterReceiver(mNetworkDetectReceiver);
        super.onDestroy();
    }

    private void checkInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            isConnected =false;

            if(done == 0){
                getAPIStartup();
            }
            //Toast.makeText(getApplicationContext(),"INTERNET NOT CONNECTED",Toast.LENGTH_SHORT);
            showNoConnectionSnackBar("Connected", isConnected, 1500);
        } else {
            isConnected= true;
//               Toast.makeText(getApplicationContext(),"INTERNET  CONNECTED",Toast.LENGTH_SHORT);
            showNoConnectionSnackBar("No internet connection found", isConnected,5000);

        }
    }

    private  void showNoConnectionSnackBar(String message, boolean isConnected, int duration) {

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, duration);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        if (isConnected){
            sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        }else{
            sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.setAction("Turn On", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent internetOptionsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(internetOptionsIntent, Constanta.WIFI_ENABLE_REQUEST);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        }
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constanta.WIFI_ENABLE_REQUEST) {

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
