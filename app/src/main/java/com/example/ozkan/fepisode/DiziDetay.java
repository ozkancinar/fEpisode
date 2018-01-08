package com.example.ozkan.fepisode;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class DiziDetay extends AppCompatActivity {
    private TabHost tabHost;
    //private Intent intent;
    String imdbID;
    int sezonSayisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dizi_detay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Geri butonu
        getSupportActionBar().setTitle("Dizi Adı"); // Activity label

        Intent myIntent = getIntent();
        imdbID = myIntent.getStringExtra("imdbID");
        sezonSayisi = myIntent.getIntExtra("sezonlar",0);

        tabHost = (TabHost)findViewById(R.id.tabHost_dizi);
        LocalActivityManager mlam = new LocalActivityManager(this, false);
        mlam.dispatchCreate(savedInstanceState);
        tabHost.setup(mlam);


        for(int i=1;i<=sezonSayisi;i++){
            TabHost.TabSpec tabspec = tabHost.newTabSpec("t"+i);
            Intent intent = new Intent(this, DiziDetay_inner.class);
            intent.putExtra("val",i);
            tabspec.setContent(intent);
            tabspec.setIndicator(String.valueOf(i));
            tabHost.addTab(tabspec);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalActivityManager mlam = new LocalActivityManager(this, false);
        mlam.dispatchPause(isFinishing());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalActivityManager mlam = new LocalActivityManager(this, false);
        mlam.dispatchResume();
    }
}
