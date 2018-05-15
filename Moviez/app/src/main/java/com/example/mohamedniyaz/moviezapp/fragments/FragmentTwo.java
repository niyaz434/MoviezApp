package com.example.mohamedniyaz.moviezapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.activity.MovieIdActivity;
import com.example.mohamedniyaz.moviezapp.modules.AdapterModel;
import com.example.mohamedniyaz.moviezapp.modules.GenereClass;
import com.example.mohamedniyaz.moviezapp.modules.MovieId;
import com.example.mohamedniyaz.moviezapp.modules.SpokenClass;
import com.example.mohamedniyaz.moviezapp.rest.ApiClient;
import com.example.mohamedniyaz.moviezapp.rest.ApiInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FragmentTwo extends Fragment{
    TextView title,description,rating,rating_count,genre,language;
    SimpleDraweeView fresco_image;
    ArrayList<AdapterModel> arrayList = new ArrayList<>();

    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/" );
    private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public int movieId;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two,container,false);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingToolbar);

        final FloatingActionButton fab;
        final boolean[] flag = {true}; // true if first icon is visible, false if second one is visible.

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag[0]){

                    fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favourite));
                    flag[0] = false;

                }else if(!flag[0]){

                    fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favourite_border));
                    flag[0] = true;

                }

            }
        });






        description = (TextView)view.findViewById(R.id.description_text);
        rating  = (TextView)view.findViewById(R.id.rating_text);
        rating_count = (TextView)view.findViewById(R.id.rating_count_text);
        genre = (TextView)view.findViewById(R.id.genre_text);
        fresco_image = (SimpleDraweeView)view.findViewById(R.id.toolbarImage) ;
        language = (TextView)view.findViewById(R.id.language_text);


        Intent intent = getActivity().getIntent();
        int idNo = intent.getIntExtra("Int",0);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieId> call = apiService.getMovieDetails(idNo,API_KEY);
        call.enqueue(new Callback<MovieId>() {
            @Override
            public void onResponse(Call<MovieId> call, Response<MovieId> response) {

                String title_name = response.body().getOriginal_title();
                Log.d(TAG, "Title name: "+title_name);
                String overview = response.body().getOverview();
                float vote_average = response.body().getVote_average();
                ArrayList<GenereClass> genereClasses = (ArrayList<GenereClass>) response.body().getGenres();
                ArrayList<SpokenClass> spokenClasses = (ArrayList<SpokenClass>) response.body().getSpoken_languages();
                int vote_count  = response.body().getVote_count();
                String backdrop_path = response.body().getBackdropPath();

                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder1 = new StringBuilder();


                    collapsingToolbarLayout.setTitle(title_name);
                    description.setText(overview);
                    rating.setText(" "+vote_average);
                    rating_count.setText(" "+vote_count);
                    fresco_image.setImageURI(uri + backdrop_path);


                    System.out.println("Nope: "+overview);
                    System.out.println("Array"+genereClasses);
                    for(int j = 0;j<genereClasses.size();j++){
                        if (stringBuilder.length() > 0 ) {
                            stringBuilder.append(", ");
                        }

                        stringBuilder.append(genereClasses.get(j).getName().toString());
                        System.out.println("String Builder"+stringBuilder.toString());
                        System.out.println("ArrayIn "+genereClasses.get(j).getName().toString());
                    }

                    for(int k =0; k < spokenClasses.size();k++){
                        if(stringBuilder1.length() >0){
                            stringBuilder1.append(", ");
                        }
                        stringBuilder1.append(spokenClasses.get(k).getName().toString());

                    }

                    stringBuilder.append('.');
                    stringBuilder1.append('.');
                    genre.setText(stringBuilder.toString());
                    language.setText(stringBuilder1.toString());

                Log.d(TAG, "onResponse: ");

            }

            @Override
            public void onFailure(Call<MovieId> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



        return view;
    }
}
