package com.example.ozkan.fepisode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);
        
        Intent intent = getIntent();
        bolum = intent.getIntExtra("val",0);

        listDetay = (ListView) findViewById(R.id.list_detay);
        adapter = new ListViewAdapter(DiziDetay.this,bolumAdlari,bolumAciklamalari,bolumPosterleri, "detay");
        listDetay.setAdapter(adapter);
        listDetay.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }
}
