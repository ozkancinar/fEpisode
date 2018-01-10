package com.example.ozkan.fepisode;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ozkan on 1/10/18.
 */

public class FetchThread extends Thread {
    private String imdbID;
    private int sezonSayisi;
    ArrayList<Intent> intentList;
    public FetchThread(String imdbID, int sezonSayisi, ArrayList<Intent> intentList) {
        this.imdbID = imdbID;
        this.sezonSayisi = sezonSayisi;
        this.intentList = intentList;
    }

    @Override
    public void run() {
        for(int i=1;i<sezonSayisi;i++) {
            dizi dizi1 = new dizi();
            FetchImdb fetch1 = new FetchImdb(imdbID, String.valueOf(i+1));
            try {
                dizi1 = fetch1.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Intent intent = intentList.get(i);
            Log.i("index", String.valueOf(i));
            intent.putExtra("titles",dizi1.getTitleArray());
            intent.putExtra("descs", dizi1.getDescArray());
            intent.putExtra("imgs", dizi1.getImageArray());

        }
    }
}
