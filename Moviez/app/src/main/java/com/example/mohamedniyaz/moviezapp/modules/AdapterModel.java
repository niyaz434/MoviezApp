package com.example.mohamedniyaz.moviezapp.modules;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AdapterModel implements Parcelable {

    String title_name;
    String overview;
    float vote_average;
    int vote_count;
    List <GenereClass> genereClasses;
    String backdroppath;
    List<SpokenClass>spokenClasses;

    public AdapterModel(String title_name, String overview, float vote_average, int vote_count, List<GenereClass> genereClasses, String backdroppath,List<SpokenClass>spokenClasses) {
        this.title_name = title_name;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.genereClasses = genereClasses;
        this.backdroppath = backdroppath;
        this.spokenClasses = spokenClasses;
    }

    private AdapterModel(Parcel in) {
        this.title_name = in.readString();
        this.overview = in.readString();
      this.vote_average = in.readFloat();
        this.vote_count = in.readInt();
       this.genereClasses = in.readArrayList(null);
        this.backdroppath = in.readString();
        this.spokenClasses = in.readArrayList(null );

    }



    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public List<GenereClass> getGenereClasses() {
        return genereClasses;
    }

    public void setGenereClasses(List<GenereClass> genereClasses) {
        this.genereClasses = genereClasses;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }
    public List<SpokenClass> getSpokenClasses() {
        return spokenClasses;
    }

    public void setSpokenClasses(List<SpokenClass> spokenClasses) {
        this.spokenClasses = spokenClasses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title_name);
        dest.writeString(overview);
        dest.writeFloat(vote_average);
        dest.writeInt(vote_count);
        dest.writeList(genereClasses);
        dest.writeString(backdroppath);
        dest.writeList(spokenClasses);

    }


    public static final Creator<AdapterModel> CREATOR = new Creator<AdapterModel>() {
        public AdapterModel createFromParcel(Parcel in) {
            return new AdapterModel(in);
        }

        public AdapterModel[] newArray(int size) {
            return new AdapterModel[size];

        }
    };
}
