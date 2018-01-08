package com.example.ozkan.fepisode;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    ArrayList<String> dizi_adlari;
    ArrayList<String> dizi_aciklamalari;
    ArrayList<String> poster_linkleri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btn1);
        edttext = (EditText) findViewById(R.id.edtxt1);
        listView = (ListView) findViewById(R.id.list_diziler);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item,fruits); // autocomplete için adapter


        //new FetchImdb().execute();

        Search();

    }

    public void Search(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImdbId();

                adapter = new ListViewAdapter(MainActivity.this,dizi_adlari,dizi_aciklamalari,poster_linkleri, "main");
                listView.setAdapter(adapter);
                listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, DiziDetay.class);
                        intent.putExtra("imdbID",imdbId);
                        intent.putExtra("sezonlar", sezonSayisi);
                        startActivity(intent);
                    }
                });
                //new FetchImdb(imdbId).execute();
            }
        });
    }

    public void getImdbId() {
        // imdb ID'yi OMDB API'dan oku
                dizi_adlari = new ArrayList<>();
                dizi_aciklamalari = new ArrayList<>();
                poster_linkleri = new ArrayList<>();
                WebService service = new WebService(MainActivity.this, edttext.getText().toString().replace(" ","+"));
                imdbId = service.imdbId; // IMDB ID
                dizi_adlari.add(service.diziAd);
                dizi_aciklamalari.add(service.diziAciklama);
                poster_linkleri.add(service.diziPoster);
                sezonSayisi = service.sezonSayisi;
                Log.i("imdbid", imdbId);
                Log.e("name", String.valueOf(dizi_adlari.get(0)));

    }


}



