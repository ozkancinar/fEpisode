package com.example.ozkan.fepisode;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ozkan.fepisode.adapters.CustomAutoCompleteArrayAdapter;
import com.example.ozkan.fepisode.adapters.CustomAutoCompleteView;
import com.example.ozkan.fepisode.db.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txt1, txt2;
    String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};
    Button btnSearch;
    EditText edttext;
    ListView listView;
    ImageButton btnAdd;
    // http://www.omdbapi.com/?apikey=ba89b089&type=series&t=friends
    String URL = "http://www.omdbapi.com/?apikey=";
    String API = "ba89b089";
    String type = "series";
    String imdbId;
    int sezonSayisi;
    //String title = "";

    // ListView değişkenleri
    ListViewAdapter adapter;
    LVAdapter_Acilis adapter_acilis;
    ArrayList<String> dizi_adlari;
    ArrayList<String> dizi_aciklamalari, dizi_sabitAciklamalari;
    ArrayList<String> poster_linkleri;
    ArrayList<String> dizi_imdbid;
    ArrayList<Integer> dizi_sezonSayisi;
    MyDbHelper myDbHelper;

    // CustomAutoComplete
    ArrayAdapter<dizi> customAutoAdapter;
    CustomAutoCompleteView customAutoComplete;
    CustomAutoCompleteArrayAdapter customAutoCompleteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_diziler);

        createCustomAutoComplete();
        acilis();
    }


    private void createCustomAutoComplete() {
        try{
            customAutoComplete = (CustomAutoCompleteView) findViewById(R.id.customAutoTxt);
            customAutoComplete.setClickable(true);
            customAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                    LinearLayout rl = (LinearLayout) arg1;
                    TextView tv = (TextView) rl.getChildAt(1);
                    customAutoComplete.setText(tv.getText().toString());
                    Search(tv.getText().toString());
                }
            });

            // add the listener so it will tries to suggest while the user types
            customAutoComplete.addTextChangedListener(new CustomAutoCompleteTextListener(this));

            // ObjectItemData has no value at first
            dizi[] ObjectItemData = new dizi[0];

            // set the custom ArrayAdapter
            customAutoAdapter = new CustomAutoCompleteArrayAdapter(this, R.layout.custom_auto_complete_main, ObjectItemData);
            customAutoComplete.setAdapter(customAutoAdapter);
        }  catch (NullPointerException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    private void acilis() {
        dizi_adlari = new ArrayList<>();
        dizi_aciklamalari = new ArrayList<>();
        poster_linkleri = new ArrayList<>();
        dizi_imdbid = new ArrayList<>();
        dizi_sezonSayisi = new ArrayList<>();
        dizi_sabitAciklamalari = new ArrayList<>();
        myDbHelper = new MyDbHelper(this);
        Cursor cursor = myDbHelper.tumDiziler();

        if(cursor!=null && cursor.getCount()>0){
           // Log.e("cursor", String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            do {

                dizi_imdbid.add(cursor.getString(2).toString()); // IMDB ID
                dizi_adlari.add(cursor.getString(1).toString());

                List<Integer> listem = myDbHelper.sonIzlenenBolum(cursor.getString(2));
                dizi_aciklamalari.add("\n Season: " + listem.get(0).toString() + " Episode: "+listem.get(1).toString());
                dizi_sabitAciklamalari.add(cursor.getString(3));
                poster_linkleri.add(cursor.getString(4).toString());
                dizi_sezonSayisi.add(Integer.valueOf(cursor.getString(5)));

            } while (cursor.moveToNext());
            cursor.close();

        }

        adapter_acilis = new LVAdapter_Acilis(MainActivity.this,dizi_adlari,dizi_aciklamalari,
                poster_linkleri,dizi_imdbid);
        listView.setAdapter(adapter_acilis);
        listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DiziDetay.class);
                intent.putExtra("imdbID",dizi_imdbid.get(i));
                String[] array = dizi_aciklamalari.get(1).split(" ");
                Log.e("click", dizi_adlari.get(i));
                intent.putExtra("sezonlar", dizi_sezonSayisi.get(i));
                intent.putExtra("diziAdi", dizi_adlari.get(i));
                intent.putExtra("diziAciklama", dizi_aciklamalari.get(i));
                intent.putExtra("diziImg", poster_linkleri.get(i));
                intent.putExtra("acilisSezon", array[2]);
                startActivity(intent);
            }
        });
    }

    public void Search(final String title){
        Log.e("Search", title);
        WebService service = new WebService(MainActivity.this, title.trim().replace(" ","+"));
        /*
        getImdbId(title);
        Log.e("serc", imdbId);
        Intent intent = new Intent(MainActivity.this, DiziDetay.class);
        intent.putExtra("imdbID",imdbId);
        intent.putExtra("sezonlar", sezonSayisi);
        intent.putExtra("diziAdi", dizi_adlari.get(0));
        intent.putExtra("diziAciklama", dizi_aciklamalari.get(0));
        intent.putExtra("diziImg", poster_linkleri.get(0));
        startActivity(intent);*/

        /*
        adapter = new ListViewAdapter(MainActivity.this,dizi_adlari,dizi_aciklamalari,poster_linkleri, "main",imdbId);
        listView.setAdapter(adapter);
        listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DiziDetay.class);
                intent.putExtra("imdbID",imdbId);
                intent.putExtra("sezonlar", sezonSayisi);
                intent.putExtra("diziAdi", dizi_adlari.get(0));
                intent.putExtra("diziAciklama", dizi_aciklamalari.get(0));
                intent.putExtra("diziImg", poster_linkleri.get(0));
                startActivity(intent);
            }
        });*/
        //new FetchImdb(imdbId).execute();


    }

    public void getImdbId(String title) {
        // imdb ID'yi OMDB API'dan oku
                dizi_adlari = new ArrayList<>();
                dizi_aciklamalari = new ArrayList<>();
                poster_linkleri = new ArrayList<>();
                WebService service = new WebService(MainActivity.this, title.trim().replace(" ","+"));
                imdbId = service.imdbId; // IMDB ID
                dizi_adlari.add(service.diziAd);
                dizi_aciklamalari.add(service.diziAciklama);
                poster_linkleri.add(service.diziPoster);
                sezonSayisi = service.sezonSayisi;
                Log.i("imdbid", imdbId);
                Log.e("name", String.valueOf(dizi_adlari.get(0)));

    }


}



