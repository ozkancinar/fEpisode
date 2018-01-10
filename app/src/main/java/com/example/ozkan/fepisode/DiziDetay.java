package com.example.ozkan.fepisode;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DiziDetay extends AppCompatActivity {
    ArrayList<String> airDate;
    ArrayList<String> titleArray;
    ArrayList<String> descArray;
    ArrayList<String> episodeNumber;
    ArrayList<String> imageArray;
    ArrayList<Intent> intentList;
    private TabHost tabHost;
    //private Intent intent;
    String imdbID;
    int sezonSayisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Context işlemleri
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dizi_detay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Geri butonu
        getSupportActionBar().setTitle("Dizi Adı"); // Activity label

        // Değişken tanımları
        airDate = new ArrayList<>();
        titleArray = new ArrayList<>();
        descArray = new ArrayList<>();
        episodeNumber = new ArrayList<>();
        imageArray = new ArrayList<>();
        intentList = new ArrayList<>();

        // MainActivity'den gelen verileri karşıla
        Intent myIntent = getIntent();
        imdbID = myIntent.getStringExtra("imdbID");
        sezonSayisi = myIntent.getIntExtra("sezonlar",0);

        // TabHost oluştur
        tabHost = (TabHost)findViewById(R.id.tabHost_dizi);
        LocalActivityManager mlam = new LocalActivityManager(this, false);
        mlam.dispatchCreate(savedInstanceState);
        tabHost.setup(mlam);

        // Dizinin sezon sayısı kadar sekme oluştur
        for(int i=1;i<=sezonSayisi;i++){
            TabHost.TabSpec tabspec = tabHost.newTabSpec("t"+i);
            Intent intent = new Intent(this, DiziDetay_inner.class);
            intent.putExtra("val",i);
            intent.putExtra("imdbid",imdbID);
            // Dizinin yalnızca birinci sezonunun verilerini çek
            if(i==1){
                dizi dizi1 = new dizi();
                FetchImdb fetch1 = new FetchImdb(imdbID, String.valueOf(i));
                try {
                    dizi1 = fetch1.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                intent.putExtra("titles",dizi1.getTitleArray());
                intent.putExtra("descs", dizi1.getDescArray());
                intent.putExtra("imgs", dizi1.getImageArray());
            }

            tabspec.setContent(intent);
            intentList.add(intent);
            tabspec.setIndicator(String.valueOf(i));

            tabHost.addTab(tabspec);

        }
        tabHost.setCurrentTab(0);
        // Dizi bilgilerini çeken thread
        FetchThread fetchThread = new FetchThread(imdbID,sezonSayisi,intentList);
        fetchThread.start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(DiziDetay.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
