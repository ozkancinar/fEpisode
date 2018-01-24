package com.example.ozkan.fepisode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ozkan on 1/10/18.
 */

public class FetchSingleThread extends Thread {
    private String imdbID;
    private int sezonSayisi;
    ArrayList<Intent> intentList;
    ProgressDialog progressDoalog;
    public FetchSingleThread(String imdbID, int sezonSayisi, ArrayList<Intent> intentList) {
        this.imdbID = imdbID;
        this.sezonSayisi = sezonSayisi;
        this.intentList = intentList;
    }

    @Override
    public void run() {
            dizi dizi1 = new dizi();
            FetchImdb fetch1 = new FetchImdb(imdbID, String.valueOf(sezonSayisi));
            try {
                dizi1 = fetch1.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Intent intent = intentList.get(sezonSayisi-1);
            intent.putExtra("titles",dizi1.getTitleArray());
            intent.putExtra("descs", dizi1.getDescArray());
            intent.putExtra("imgs", dizi1.getImageArray());
            intent.putExtra("finish",true);



    }

}



