package com.example.ozkan.fepisode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ozkan on 1/8/18.
 */

public class DiziDetay_inner extends Activity{
    int bolum;
    ArrayList<String> bolumAdlari;
    ArrayList<String> bolumAciklamalari;
    ArrayList<String> bolumPosterleri;
    ListViewAdapter adapter;
    ListView listDetay;
    String imdbID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);

        bolumAdlari = new ArrayList<>();
        bolumAciklamalari = new ArrayList<>();
        bolumPosterleri = new ArrayList<>();

        Intent intent = getIntent();
        bolum = intent.getIntExtra("val",0);
        imdbID = intent.getStringExtra("imdbid");
        bolumAdlari = intent.getStringArrayListExtra("titles");
        bolumAciklamalari = intent.getStringArrayListExtra("descs");
        bolumPosterleri = intent.getStringArrayListExtra("imgs");

        listDetay = (ListView) findViewById(R.id.list_detay);
        adapter = new ListViewAdapter(this,bolumAdlari,bolumAciklamalari,bolumPosterleri, "detay",imdbID);
        listDetay.setAdapter(adapter);
        listDetay.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }
}
