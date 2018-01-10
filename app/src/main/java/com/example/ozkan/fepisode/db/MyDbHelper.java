package com.example.ozkan.fepisode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ozkan.fepisode.dizi;

import java.util.ArrayList;

/**
 * Created by ozkan on 1/10/18.
 */

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME= "dizitakip.db";
    private static final String TABLE_NAME = "diziler";
    private static final String COL_0 = "dizi_id";
    private static final String COL_1 = "dizi_adi";
    private static final String COL2 = "dizi_imdbid";


    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(dizi_id INTEGER PRIMARY KEY AUTOINCREMENT, dizi_adi TEXT, dizi_imdbid TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String adi, String imdbid){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1,adi);
        values.put(COL2,imdbid);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, values);
        if(result==-1)
            return false;
        else
            return true;
    }
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
    public Cursor tumDiziler(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        //sqLiteDatabase.close();
        return  cursor;
    }
}
