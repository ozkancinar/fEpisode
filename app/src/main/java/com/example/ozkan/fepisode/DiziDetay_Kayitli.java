package com.example.ozkan.fepisode;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.ozkan.fepisode.db.MyDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiziDetay_Kayitli extends AppCompatActivity {
    ArrayList<String> airDate;
    ArrayList<String> titleArray;
    ArrayList<String> descArray;
    ArrayList<String> episodeNumber;
    ArrayList<String> imageArray;
    ArrayList<Intent> intentList;
    private TabHost tabHost;
    //private Intent intent;
    String imdbID, diziAdi, diziAciklama, diziImg;
    int sezonSayisi, acilisSezon;
    MyDbHelper myDbHelper;
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
        diziAdi = myIntent.getStringExtra("diziAdi");
        diziAciklama = myIntent.getStringExtra("diziAciklama");
        diziImg = myIntent.getStringExtra("diziImg");

        acilisSezon = myIntent.getIntExtra("acilisSezon",0);

        myDbHelper = new MyDbHelper(this);
        List<dizi> diziList = null;
        diziList = myDbHelper.tumBolumleriGetir(imdbID);

        // TabHost oluştur
        tabHost = (TabHost)findViewById(R.id.tabHost_dizi);
        LocalActivityManager mlam = new LocalActivityManager(this, false);
        mlam.dispatchCreate(savedInstanceState);
        tabHost.setup(mlam);

        // Dizinin sezon sayısı kadar sekme oluştur
        for(int i=1;i<=sezonSayisi;i++){
            TabHost.TabSpec tabspec = tabHost.newTabSpec("t"+i);
            Intent intent = new Intent(this, DiziDetay_inner.class); // Intent olustur
            intent.putExtra("sezon",i);
            intent.putExtra("imdbid",imdbID);
            intent.putExtra("diziAdi", diziAdi);
            intent.putExtra("diziAciklama", diziAciklama);
            intent.putExtra("diziImg", diziImg);
            intent.putExtra("toplamSezon", sezonSayisi);
            titleArray = new ArrayList<>();
            descArray = new ArrayList<>();
            imageArray = new ArrayList<>();
            for(int j=0;j<diziList.size();j++){
                if(diziList.get(j).getSezon() == i){
                    int index = diziList.get(j).getBolum();
                    titleArray.add(diziList.get(j).getTitle());
                    descArray.add(diziList.get(j).getDescription());
                    imageArray.add(diziList.get(j).getImgSrc());

                }
            }
            intent.putExtra("titles",titleArray);
            intent.putExtra("descs", descArray);
            intent.putExtra("imgs", imageArray);
            tabspec.setContent(intent);
            intentList.add(intent); // daha sonra erismek icin itentlistesine kele
            tabspec.setIndicator(String.valueOf(i));
            tabHost.addTab(tabspec);

        }

        tabHost.setCurrentTab(acilisSezon-1);
        // Dizi bilgilerini çeken thread
        //FetchThread fetchThread = new FetchThread(imdbID,sezonSayisi,intentList);
        //fetchThread.start();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Geri butonuna tıklandıgında yapılacak işlemler
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(DiziDetay_Kayitli.this, MainActivity.class);
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
