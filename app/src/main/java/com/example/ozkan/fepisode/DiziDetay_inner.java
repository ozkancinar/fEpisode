package com.example.ozkan.fepisode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ozkan.fepisode.db.MyDbHelper;

import java.util.ArrayList;

/**
 * Created by ozkan on 1/8/18.
 */

public class DiziDetay_inner extends Activity{
    int bolum, sezon, toplamSezon;
    ArrayList<String> bolumAdlari;
    ArrayList<String> bolumAciklamalari;
    ArrayList<String> bolumPosterleri;
    ArrayList<Boolean> bolumIzlenen;
    LVAdapter_Sezonlar adapter;
    ListView listDetay;
    String imdbID, diziAdi, diziAciklama, diziImg;
    MyDbHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);

        bolumAdlari = new ArrayList<>();
        bolumAciklamalari = new ArrayList<>();
        bolumPosterleri = new ArrayList<>();
        bolumIzlenen = new ArrayList<>();
        // Threadden listiewde gösterilecek verileri al
        Intent intent = getIntent();
        diziAdi = intent.getStringExtra("diziAdi");
        sezon = intent.getIntExtra("sezon",0);
        imdbID = intent.getStringExtra("imdbid");
        bolumAdlari = intent.getStringArrayListExtra("titles");
        bolumAciklamalari = intent.getStringArrayListExtra("descs");
        bolumPosterleri = intent.getStringArrayListExtra("imgs");
        diziAciklama = intent.getStringExtra("diziAciklama");
        diziImg = intent.getStringExtra("diziImg");
        toplamSezon = intent.getIntExtra("toplamSezon",0);
        boolean finish = intent.getBooleanExtra("finish",false);


        myDbHelper = new MyDbHelper(this);
        for(int i=0;i<bolumAdlari.size();i++){
            boolean izlendi = myDbHelper.isBolumWatched(imdbID,sezon,(i+1));
            if(izlendi)
                bolumIzlenen.add(true);
            else
                bolumIzlenen.add(false);
        }

        // Listview oluştur
        listDetay = (ListView) findViewById(R.id.list_detay);
        adapter = new LVAdapter_Sezonlar(this,bolumAdlari,bolumAciklamalari,bolumPosterleri, "detay",
                imdbID,sezon, bolumIzlenen, diziAdi, diziAciklama, diziImg, toplamSezon);
        listDetay.setAdapter(adapter);
        listDetay.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }
}
