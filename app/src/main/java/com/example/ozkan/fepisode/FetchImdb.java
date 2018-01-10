package com.example.ozkan.fepisode;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ozkan on 12/6/17.
 */

public class FetchImdb extends AsyncTask <String, Void, dizi>{
    ArrayList<String> airDate = new ArrayList<>();
    ArrayList<String> array = new ArrayList<>();
    ArrayList<String> titleArray = new ArrayList<>();
    ArrayList<String> descArray = new ArrayList<>();
    ArrayList<String> episodeNumber = new ArrayList<>();
    ArrayList<String> imageArray = new ArrayList<>();

    String URL;
    public FetchImdb(String ID, String sezon) {
        // Örnek sorgu= this.URL = "http://www.imdb.com/title/"+ID+"/episodes?season=1"
        this.URL = "http://www.imdb.com/title/"+ID+"/episodes?season="+sezon;
    }

    @Override
    protected void onPostExecute(dizi dizi) {
        super.onPostExecute(dizi);
    }

    @Override
    protected dizi doInBackground(String... strings) {
        dizi dizi = new dizi();
        try {
            Log.e("fetch",URL);
            Document doc = Jsoup.connect(URL).get();
            Elements div = doc.select("div.list.detail.eplist > div.list_item");
            Elements dates = div.select("div.info > div.airdate");
            Elements titles = div.select("div.info > strong");
            Elements descs = div.select("div.info > div.item_description");
            Elements eNumbers = div.select("div.image > a");
            Elements imgs = eNumbers.select("div.hover-over-image > img[src]");

            /*for(Element element : div){
                Log.e("el", element.text());
                array.add(element.text());
            }*/

            // Get episode numbers
            for(Element eNumber: eNumbers){
                //Log.i("eps",eNumber.text());
                episodeNumber.add(eNumber.text());
            }


            // Get titles
            for(Element title : titles){
                //Log.i("title", title.text());
                titleArray.add(title.text());
            }

            // Get airdates
            for(Element a:dates){
                //Log.i("air",a.text());
                airDate.add(a.text());
            }

            // Get descs
            for(Element desc:descs){
                //Log.i("desc", desc.text());
                descArray.add(desc.text());
            }

            // Get images
            for(Element img:imgs){
                //Log.i("img", img.attr("src"));
                imageArray.add(img.attr("src"));
            }

            dizi.setTitleArray(this.titleArray);
            dizi.setAirDate(this.airDate);
            dizi.setDescArray(this.descArray);
            dizi.setImageArray(this.imageArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dizi;
    }
}
