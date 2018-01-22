package com.example.ozkan.fepisode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ozkan.fepisode.dizi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozkan on 1/10/18.
 */

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME= "dizitakip.db";
    private static final String TABLE_NAME = "diziler";
    private static final String COL_0 = "dizi_id";
    private static final String COL_1 = "dizi_adi";
    private static final String COL2 = "dizi_imdbid";
    private static final String COL_3 = "dizi_aciklama";
    private static final String COL_4 = "dizi_img";
    private static final String COL_5 = "dizi_sezon";
    private static final String TABLE_BOLUM = "bolumler";
    private static final String B_COL_3 = "bolum_sezon";
    private static final String B_COL_4 = "bolum_bolum";
    private static final String B_COL_5 = "bolum_adi";
    private static final String B_COL_6 = "bolum_aciklama";
    private static final String B_COL_7 = "bolum_img";
    private static final String TABLE_TUMBOLUMLER = "tumbolumler";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(dizi_id INTEGER PRIMARY KEY AUTOINCREMENT, dizi_adi TEXT, dizi_imdbid TEXT, dizi_aciklama TEXT, dizi_img TEXT, dizi_sezon INTEGER)");
        db.execSQL("create table "+TABLE_BOLUM+"(dizi_id INTEGER PRIMARY KEY AUTOINCREMENT, dizi_adi TEXT, dizi_imdbid TEXT, bolum_sezon INTEGER, bolum_bolum INTEGER)");
        db.execSQL("create table "+TABLE_TUMBOLUMLER+"(dizi_id INTEGER PRIMARY KEY AUTOINCREMENT, bolum_adi TEXT, dizi_imdbid TEXT ,bolum_aciklama TEXT, bolum_img TEXT ,bolum_sezon INTEGER, bolum_bolum INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOLUM);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TUMBOLUMLER);
        onCreate(db);

    }
    // Diziyi izlenenler listesine ekle
    public boolean insertData(String adi, String imdbid, String diziAciklama, String diziImg, int sezonSayisi){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1,adi);
        values.put(COL2,imdbid);
        values.put(COL_3, diziAciklama);
        values.put(COL_4, diziImg);
        values.put(COL_5, sezonSayisi);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, values);
        if(result==-1)
            return false;
        else{
            Log.e("Kayit basarili", adi);
            return true;
        }

    }

    // Dizi izlenenler listesinde mi kontrol et
    public boolean isWatched(String imdbid){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME +" WHERE dizi_imdbid = '"+imdbid+"'", null);
        boolean sonuc = false;

        while (cursor.moveToNext()){
            Log.e("adqwe",cursor.getString(1).toString());
            sonuc=true;
        }

        return sonuc;
    }
    // Kayıtlı tüm dizileri getir
    public Cursor tumDiziler(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        //sqLiteDatabase.close();
        return  cursor;
    }

    // İzlenen bölümü kaydet
    public boolean bolumKaydet(String dizi_adi, String imdbid, int sezon, int bolum){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, dizi_adi);
        values.put(COL2, imdbid);
        values.put(B_COL_3, sezon);
        values.put(B_COL_4, bolum);
        long result = sqLiteDatabase.insert(TABLE_BOLUM,null,values);
        if (result==-1)
            return false;
        else
            return true;
    }

    // Bölüm izlenenler listesinde mi kontrol et
    public boolean isBolumWatched(String imdbid, int sezon, int bolum){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_BOLUM +" WHERE dizi_imdbid = '"+imdbid+"' AND bolum_sezon = "+sezon+" AND bolum_bolum = "+bolum+"", null);
        boolean sonuc = false;

        while (cursor.moveToNext()){
            Log.e("Bolum izlenmis",cursor.getString(1).toString());
            sonuc=true;
        }
        return sonuc;
    }

    // Bölümü izlenener listesinden sil
    public boolean bolumSil(String imdbid, int sezon, int bolum){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
        db.delete(TABLE_BOLUM, COL2 + " = '"+imdbid+"' AND "+B_COL_3+" = "+sezon+" AND "+B_COL_4+" = "+bolum+"",
                null);
        }catch (SQLException mSQLException) {
            Log.e("Bolum silinemedi", "bolum silinemedi >>" + mSQLException.toString());
            throw mSQLException;

        }
        return true;

    }
    // son izlenen bolumu bul
    public List<Integer> sonIzlenenBolum(String imdbid){
        List<Integer> liste = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select bolum_sezon, bolum_bolum from " + TABLE_BOLUM +" WHERE dizi_imdbid = '"+imdbid+"'", null);
        int sezon = 0, bolum = 0;
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
               if(cursor.getInt(0)>sezon){
                   sezon=cursor.getInt(0);
                   bolum=0;
                   if(cursor.getInt(1)>bolum)
                       bolum=cursor.getInt(1);
               }else if(cursor.getInt(0)==sezon){
                   if(cursor.getInt(1)>bolum)
                       bolum=cursor.getInt(1);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        liste.add(sezon);
        liste.add(bolum);

        return liste;
    }

    // Favori dizinin tüm bölümlerini kaydet
    public boolean tumBolumleriKaydet(String dizi_imdbid, String bolum_adi, String bolum_aciklama,
                                      String bolum_img, int bolum_sezon, int bolum_bolum){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, dizi_imdbid);
        values.put(B_COL_5, bolum_adi);
        values.put(B_COL_6, bolum_aciklama);
        values.put(B_COL_7, bolum_img);
        values.put(B_COL_3, bolum_sezon);
        values.put(B_COL_4, bolum_bolum);
        long result = sqLiteDatabase.insert(TABLE_TUMBOLUMLER,null,values);
        if (result==-1)
            return false;
        else{
            sqLiteDatabase.close();
            return true;
        }
    }

    // Favori dizinin tüm bölümlerini getir
    public List<dizi> tumBolumleriGetir(String imdbid){
        List<dizi> diziList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select bolum_adi, bolum_aciklama, bolum_img, bolum_sezon, bolum_bolum from " + TABLE_TUMBOLUMLER +" WHERE dizi_imdbid = '"+imdbid+"'", null);
        cursor.moveToFirst();
        do {
            dizi dizi = new dizi();
            dizi.setTitle(cursor.getString(0));
            dizi.setDescription(cursor.getString(1));
            dizi.setImgSrc(cursor.getString(2));
            dizi.setSezon(cursor.getInt(3));
            dizi.setBolum(cursor.getInt(4));
            diziList.add(dizi);
        }while (cursor.moveToNext());
        return diziList;
    }

}
