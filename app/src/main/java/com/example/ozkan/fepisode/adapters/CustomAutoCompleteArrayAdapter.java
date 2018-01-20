package com.example.ozkan.fepisode.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ozkan.fepisode.MainActivity;
import com.example.ozkan.fepisode.R;
import com.example.ozkan.fepisode.dizi;

import java.io.InputStream;

/**
 * Created by ozkan on 1/20/18.
 */

public class CustomAutoCompleteArrayAdapter extends ArrayAdapter<dizi> {
    Context context;
    int resource;
    dizi[] diziArray = null;
    public dizi objectItem;

    public CustomAutoCompleteArrayAdapter(@NonNull Context context, int resource, dizi[] data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.diziArray = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try{

            /*
             * The convertView argument is essentially a "ScrapView" as described is Lucas post
             * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
             * It will have a non-null value when ListView is asking you recycle the row layout.
             * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
             */
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
                convertView = inflater.inflate(resource, parent, false);
            }

            // object item based on the position

            objectItem = diziArray[position];
            Log.e("array", objectItem.getTitle());
            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.txt_cust_diziAdi);
            ImageView imgButton = (ImageView) convertView.findViewById(R.id.img_cust_img);

            new DownloadImageTask(imgButton).execute(objectItem.getImgSrc());
            textViewItem.setText(objectItem.getTitle());

            // in case you want to add some style, you can do something like:
            //textViewItem.setBackgroundColor(Color.CYAN);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }

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


