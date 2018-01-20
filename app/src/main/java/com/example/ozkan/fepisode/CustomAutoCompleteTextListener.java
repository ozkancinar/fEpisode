package com.example.ozkan.fepisode;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.ozkan.fepisode.adapters.CustomAutoCompleteArrayAdapter;

/**
 * Created by ozkan on 1/20/18.
 */

public class CustomAutoCompleteTextListener implements TextWatcher {
    Context context;

    public CustomAutoCompleteTextListener(Context context) {
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
        try{

            // if you want to see in the logcat what the user types
            Log.e("textchangedlistener", "User input: " + userInput);


            MainActivity mainActivity = ((MainActivity) context);

            // update the adapater
            mainActivity.customAutoAdapter.notifyDataSetChanged();

            // Verinin çekildiği yer
            // get suggestions from the database
            //dizi[] myObjs = mainActivity.databaseH.read(userInput.toString());
            dizi[] dizim = new dizi[1];
            dizim[0] = new dizi();

            WebServiceA service = new WebServiceA(mainActivity, userInput.toString().trim().replace(" ","+"));
            service.search();
            //dizi dizi1 = service.search();
            /*Log.e("adp",dizi1.getTitle());


            dizim[0].setTitle(service.diziAd);
            // update the adapter
            Log.e("adapter",dizim[0].getTitle());
            mainActivity.customAutoAdapter = new CustomAutoCompleteArrayAdapter(mainActivity, R.layout.custom_auto_complete_main, dizim);

            mainActivity.customAutoComplete.setAdapter(mainActivity.customAutoAdapter);
            mainActivity.customAutoComplete.showDropDown();*/

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
