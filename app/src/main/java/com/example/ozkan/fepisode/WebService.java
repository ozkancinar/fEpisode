package com.example.ozkan.fepisode;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
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

public class WebService {
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
    public WebService(Context context, String TITLE) {
        this.context = context;
        this.TITLE = TITLE;
        search();
        Log.i("tit", imdbId);

    }

    public void search() {

                List<Header> headers = new ArrayList<>();
                headers.add(new BasicHeader("Accept", "application/json"));

                String base_url = URL + API + "&type=" + type + "&t=" + TITLE;
                AsyncHttpClient client = new AsyncHttpClient();

                RestClient.get(context, base_url,
                        headers.toArray(new Header[headers.size()]), null,
                        new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {

                                    String idM = response.get("imdbID").toString();
                                    diziAd = response.get("Title").toString();
                                    diziAciklama = response.get("Year").toString() + "    IMDB: " + response.get("imdbRating").toString();
                                    imdbId = idM;
                                    sezonSayisi = Integer.parseInt(response.get("totalSeasons").toString());
                                    diziPoster = response.get("Poster").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        WebService.id = id;
    }
}
