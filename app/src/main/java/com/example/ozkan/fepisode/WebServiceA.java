package com.example.ozkan.fepisode;

import android.content.Context;
import android.util.Log;

import com.example.ozkan.fepisode.adapters.CustomAutoCompleteArrayAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Created by ozkan on 12/6/17.
 */

public class WebServiceA {
    String URL = "http://www.omdbapi.com/?apikey=";
    String API = "ba89b089";
    String type = "series";
    Context context;
    String TITLE="";
    static public String imdbId = "";
    static public String diziAd = "", diziAciklama = "", diziPoster = "";
    //static public ArrayList<String> diziAd_list;
   // static public ArrayList<String> diziAciklama_list;
    static public int sezonSayisi = 0;
    private static String id;
    AsyncHttpClient asyncHttpClient;
    public WebServiceA(Context context, String TITLE) {
        this.context = context;
        this.TITLE = TITLE;
        //search();
        Log.e("tit", imdbId);
    }

    public void search() {

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));

        String base_url = URL + API + "&type=" + type + "&t=" + TITLE;
        dizi dizi1 = new dizi();
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(context, base_url, headers.toArray(new Header[headers.size()]), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    MainActivity mainActivity = ((MainActivity) context);
                    mainActivity.customAutoAdapter.notifyDataSetChanged();
                    if(response != null || response.length() != 0){
                        dizi[] dizi = new dizi[1];
                        dizi[0] = new dizi();
                        dizi[0].setImdbid(response.getString("imdbID"));
                        dizi[0].setTitle(response.getString("Title"));
                        dizi[0].setDescription(response.get("Title").toString());
                        dizi[0].setDescription((response.get("Year").toString() + "    IMDB: " + response.get("imdbRating").toString()));

                        if(response.get("totalSeasons") instanceof String){
                            if(response.get("totalSeasons").toString().trim().equals("N/A")){
                                dizi[0].setSezonSayisi(0);
                            }else{
                                dizi[0].setSezonSayisi(Integer.parseInt(response.get("totalSeasons").toString()));
                            }
                        }else{
                            sezonSayisi = 0;
                        }

                        dizi[0].setImgSrc(response.get("Poster").toString());

                        mainActivity.customAutoAdapter = new CustomAutoCompleteArrayAdapter(mainActivity, R.layout.custom_auto_complete_main, dizi);

                        mainActivity.customAutoComplete.setAdapter(mainActivity.customAutoAdapter);
                        mainActivity.customAutoComplete.showDropDown();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


      /*  RestClient.get(context, base_url,
                headers.toArray(new Header[headers.size()]), null,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {

                            if(response != null || response.length() != 0){
                            imdbId = response.get("imdbID").toString();
                            diziAd = response.get("Title").toString();
                            diziAciklama = (response.get("Year").toString() + "    IMDB: " + response.get("imdbRating").toString());

                            Log.e("wsadiziadi", diziAd);

                            if(response.get("totalSeasons") instanceof String){
                                if(response.get("totalSeasons").toString().trim().equals("N/A")){
                                    sezonSayisi = 0;
                                }else{
                                    sezonSayisi = Integer.parseInt(response.get("totalSeasons").toString());
                                }
                            }else{
                                sezonSayisi = 0;
                            }

                            diziPoster = (response.get("Poster").toString());
                            }

                        } catch (JSONException e) {
                            Log.e("heey", "heey");
                            e.printStackTrace();
                        }
                    }
                });
            dizi dizi = new dizi();
            dizi.setTitle(diziAd);
            dizi.setDescription(diziAciklama);
            Log.e("dafuck", dizi.getDescription());
            return dizi;*/
            }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        WebServiceA.id = id;
    }
}
