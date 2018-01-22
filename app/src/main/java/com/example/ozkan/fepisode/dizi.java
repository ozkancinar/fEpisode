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
    private String title;
    private String imgSrc;
    private String description;
    private String episodes;
    private String imdbid;
    private int sezonSayisi;
    private int sezon, bolum;

    public int getSezon() {
        return sezon;
    }

    public void setSezon(int sezon) {
        this.sezon = sezon;
    }

    public int getBolum() {
        return bolum;
    }

    public void setBolum(int bolum) {
        this.bolum = bolum;
    }

    public int getSezonSayisi() {
        return sezonSayisi;
    }

    public void setSezonSayisi(int sezonSayisi) {
        this.sezonSayisi = sezonSayisi;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

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
