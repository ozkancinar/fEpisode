package com.example.ozkan.fepisode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.example.ozkan.fepisode.db.MyDbHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ozkan on 1/10/18.
 * ---- KULLANIM DIŞI ----
 */

public class FetchThread extends Thread {
    private String imdbID;
    private int sezonSayisi;
    ArrayList<Intent> intentList;
    ProgressDialog progressDoalog;
    MyDbHelper myDbHelper;
    Context context;
    public FetchThread(String imdbID, int sezonSayisi, Context context) {
        this.imdbID = imdbID;
        this.sezonSayisi = sezonSayisi;
        this.context = context;
    }

    @Override
    public void run() {
        myDbHelper = new MyDbHelper(context);
        for(int i=1;i<sezonSayisi;i++) {
            dizi dizi1 = new dizi();
            FetchImdb fetch1 = new FetchImdb(imdbID, String.valueOf(i));
            try {
                dizi1 = fetch1.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            for(int j=0;j<dizi1.getTitleArray().size();j++){
                myDbHelper.tumBolumleriKaydet(imdbID,dizi1.getTitleArray().get(j),dizi1.getDescArray().get(j),
                        dizi1.getImageArray().get(j),i,j);

            }
            Log.e("Kayit Tamam", "Sezon "+String.valueOf(i));

            /*
            Intent intent = intentList.get(i);

            intent.putExtra("titles",dizi1.getTitleArray());
            intent.putExtra("descs", dizi1.getDescArray());
            intent.putExtra("imgs", dizi1.getImageArray());
            intent.putExtra("finish",true);*/

        }

    }

}



