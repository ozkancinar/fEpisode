package com.example.ozkan.fepisode;

import java.util.ArrayList;

/**
 * Created by ozkan on 1/10/18.
 */

public class dizi {
    private ArrayList<String> airDate;
    private ArrayList<String> titleArray;
    private ArrayList<String> descArray;
    private ArrayList<String> episodeNumber;
    private ArrayList<String> imageArray;

    public ArrayList<String> getAirDate() {
        return airDate;
    }

    public void setAirDate(ArrayList<String> airDate) {
        this.airDate = airDate;
    }

    public ArrayList<String> getTitleArray() {
        return titleArray;
    }

    public void setTitleArray(ArrayList<String> titleArray) {
        this.titleArray = titleArray;
    }

    public ArrayList<String> getDescArray() {
        return descArray;
    }

    public void setDescArray(ArrayList<String> descArray) {
        this.descArray = descArray;
    }

    public ArrayList<String> getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(ArrayList<String> episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public ArrayList<String> getImageArray() {
        return imageArray;
    }

    public void setImageArray(ArrayList<String> imageArray) {
        this.imageArray = imageArray;
    }
}
