package com.example.ozkan.fepisode;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ozkan.fepisode.db.MyDbHelper;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ozkan on 4/9/17.
 */

public class LVAdapter_Sezonlar extends BaseAdapter {
    int toplamSezon;
    Context context;
    ArrayList<String> bolum_adlari;
    ArrayList<String> bolum_aciklamalari;
    ArrayList<String> bolum_posterleri;
    ArrayList<Boolean> bolum_izlenen;
    LayoutInflater inflater;
    String imdbid, diziAdi;
    MainActivity active;
    String mode, diziAciklama, diziImg;
    int sezon;
    private MyDbHelper myDbHelper;
    FetchThread fetchThread;
    public LVAdapter_Sezonlar(Context context, ArrayList<String> bolum_adlari, ArrayList<String> bolum_aciklamalari, ArrayList<String> bolum_posterleri, String mode,
                              String imdbid, int sezon, ArrayList<Boolean> bolumIzlenen, String diziAdi, String diziAciklama, String diziImg, int toplamSezon) {
        super();
        this.context = context;
        this.bolum_adlari = bolum_adlari;
        this.bolum_aciklamalari = bolum_aciklamalari;
        this.bolum_posterleri = bolum_posterleri;
        this.inflater = LayoutInflater.from(context);
        this.mode = mode;
        this.imdbid = imdbid;
        this.sezon = sezon;
        this.bolum_izlenen = bolumIzlenen;
        this.diziAdi = diziAdi;
        this.diziAciklama = diziAciklama;
        this.diziImg = diziImg;
        this.toplamSezon = toplamSezon;
    }

    @Override
    public int getCount() {

        if(bolum_adlari==null || bolum_adlari.equals(null)){
            Log.e("null","asd");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{

        }
        return bolum_adlari.size();
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // tanımlar
        fetchThread = new FetchThread(imdbid, toplamSezon, context); // Tüm bölümleri kaydet
        inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.custom_lv_sezonlar,parent,
                false);
        TextView txt_bolumAdi = (TextView)itemView.findViewById(R.id.txt_bolumAdi);
        TextView txt_bolumAciklama = (TextView)itemView.findViewById(R.id.txt_bolumAciklama);
        final ImageView img_bolumPoster = (ImageView)itemView.findViewById(R.id.img_bolum_poster);
        final ImageButton btn_bolumAdd = (ImageButton) itemView.findViewById(R.id.btn_bolumAdd);

        // Componentleri doldur
        txt_bolumAdi.setText(bolum_adlari.get(position));
        if(bolum_aciklamalari.get(position).length()>70){
            txt_bolumAciklama.setText(bolum_aciklamalari.get(position).substring(0,68)+"...");
        }else{
            txt_bolumAciklama.setText(bolum_aciklamalari.get(position));
        }
        txt_bolumAdi.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);


        // resimleri göster
        new DownloadImageTask(img_bolumPoster).execute(bolum_posterleri.get(position));

        // Ekleme butonunu tanımla
        if(bolum_izlenen.get(position)){
            btn_bolumAdd.setImageResource(R.drawable.remove32px);
        }else{
            btn_bolumAdd.setImageResource(R.drawable.eyep32px);
        }

        // butona tıklandığında veri tabanına kaydet
        myDbHelper = new MyDbHelper(context); // veritabanı bağlantısı
        btn_bolumAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bolum_izlenen.get(position)){ // bolum izlendiyse
                    boolean sonuc = myDbHelper.bolumSil(imdbid,sezon,(position+1));
                    if (sonuc){
                        bolum_izlenen.set(position,false);
                        btn_bolumAdd.setImageResource(R.drawable.eyep32px);
                    }else{
                        Toast.makeText(context,"Kaydedilirken Hata",Toast.LENGTH_SHORT).show();
                    }
                }else{ // bolum izlenmediyse
                    boolean sonuc = myDbHelper.bolumKaydet(bolum_adlari.get(position), imdbid, sezon, (position+1));
                    boolean dizi_kayitli = myDbHelper.isWatched(imdbid); // dizi daha önce kaydedildi mi?
                    Log.e("dizi_kayitli = ", String.valueOf(dizi_kayitli));
                    if(!dizi_kayitli){
                        myDbHelper.insertData(diziAdi,imdbid, diziAciklama, diziImg, toplamSezon); // eger kaydedilmediyse veri tabanına kaydet

                        fetchThread.start();
                    }
                    if(sonuc){
                        bolum_izlenen.set(position,true);
                        btn_bolumAdd.setImageResource(R.drawable.remove32px);
                        Toast.makeText(context,"Bölüm İzlendi",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"Kaydedilirken Hata",Toast.LENGTH_SHORT).show();
                    }
                }

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
