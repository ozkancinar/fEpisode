package com.example.ozkan.fepisode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ozkan on 4/9/17.
 */

public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> dizi_adlari;
    ArrayList<String> dizi_aciklamalari;
    ArrayList<String> dizi_posterleri;
    LayoutInflater inflater;
    MainActivity active;
    public ListViewAdapter(Context context, ArrayList<String> dizi_adlari, ArrayList<String> dizi_aciklamalari, ArrayList<String> dizi_posterleri) {
        super();
        this.context = context;
        this.dizi_adlari = dizi_adlari;
        this.dizi_aciklamalari = dizi_aciklamalari;
        this.dizi_posterleri = dizi_posterleri;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dizi_adlari.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.custom_listview,parent,
                false);
        TextView txt_diziAdi = (TextView)itemView.findViewById(R.id.txt_diziAdi);
        TextView txt_diziAciklama = (TextView)itemView.findViewById(R.id.txt_diziAciklama);
        final ImageView img_poster = (ImageView)itemView.findViewById(R.id.img_poster);
        final ImageButton btnAdd = (ImageButton) itemView.findViewById(R.id.btn_add);
        txt_diziAdi.setText(dizi_adlari.get(position));
        txt_diziAciklama.setText(dizi_aciklamalari.get(position));
        new DownloadImageTask(img_poster).execute(dizi_posterleri.get(position));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    btnAdd.setImageResource(R.drawable.remove);
                    Toast.makeText(context,"Takibe Alındı",Toast.LENGTH_LONG).show();
            }

        });



        return itemView;
    }




    // IMAGEVIEW'ı link ile doldurmak için yazılan class
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
