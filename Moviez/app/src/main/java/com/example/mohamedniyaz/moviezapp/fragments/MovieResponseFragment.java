package com.example.mohamedniyaz.moviezapp.fragments;

import android.content.Context;
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
import com.example.mohamedniyaz.moviezapp.database.SqliteHelper;
import com.example.mohamedniyaz.moviezapp.modules.AdapterModel;
import com.example.mohamedniyaz.moviezapp.modules.GenereClass;
import com.example.mohamedniyaz.moviezapp.modules.MovieId;
import com.example.mohamedniyaz.moviezapp.modules.SpokenClass;
import com.example.mohamedniyaz.moviezapp.rest.ApiClient;
import com.example.mohamedniyaz.moviezapp.rest.ApiInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MovieResponseFragment extends Fragment {

    //TODO Read about modifiers
    //TODO Declare in separate lines
    TextView title,description,rating,rating_count,genre,language;
    SimpleDraweeView fresco_image;
    ArrayList<AdapterModel> arrayList = new ArrayList<>();
    List<MovieId> movieIdList = new ArrayList<>();
    //TODO not required should be string
    //TODO Use constant file
    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/");
    private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public int movieId;
    //TODO initialized variable for boolean is always false
    boolean isFavourite = true;
    private SqliteHelper sqliteHelper;
    public String title_name;


    public static Fragment newInstance(int movieId){
        MovieResponseFragment movieResponseFragment = new MovieResponseFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("MOVIE_ID",movieId);
        movieResponseFragment.setArguments(mBundle);
        return movieResponseFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        movieId = bundle.getInt("MOVIE_ID");

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_two,container,false);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingToolbar);

        final FloatingActionButton fab;

        fab = view.findViewById(R.id.fab);
        sqliteHelper = new SqliteHelper(getActivity());

        final boolean[] flag = {false}; // true if first icon is visible, false if second one is visible.
        description = (TextView)view.findViewById(R.id.description_text);
        rating  = (TextView)view.findViewById(R.id.rating_text);
        rating_count = (TextView)view.findViewById(R.id.rating_count_text);
        genre = (TextView)view.findViewById(R.id.genre_text);
        fresco_image = (SimpleDraweeView)view.findViewById(R.id.toolbarImage) ;
        language = (TextView)view.findViewById(R.id.language_text);


//        Intent intent = getActivity().getIntent();
//        movieId = intent.getIntExtra("Int",0);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieId> call = apiService.getMovieDetails(movieId,API_KEY);
        call.enqueue(new Callback<MovieId>() {
            @Override
            public void onResponse(Call<MovieId> call, Response<MovieId> response) {
                title_name = response.body().getOriginal_title();
                Log.d(TAG, "Title name: "+title_name);
                String overview = response.body().getOverview();
                float vote_average = response.body().getVote_average();
                ArrayList<GenereClass> genereClasses = (ArrayList<GenereClass>) response.body().getGenres();
                ArrayList<SpokenClass> spokenClasses = (ArrayList<SpokenClass>) response.body().getSpoken_languages();
                int vote_count  = response.body().getVote_count();
                String backdrop_path = response.body().getBackdropPath();

                //TODO can use as class variable to avoid unwanted object creation and use meaningful names
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder1 = new StringBuilder();


                    collapsingToolbarLayout.setTitle(title_name);
                    description.setText(overview);
                    rating.setText(" "+vote_average);
                    rating_count.setText(" "+vote_count);
                    fresco_image.setImageURI(uri + backdrop_path);

                    //TODO Logutils to help differentiate between release and debug
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
                    if (isFavourite){
                        isFavourite = false;
                    }
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!isFavourite){
                                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favourite));
                                isFavourite = true;
                                fab.setSelected(true);
                                if (sqliteHelper.data(movieId)){
                                    sqliteHelper.update(movieId, isFavourite);
                                }else {
                                    sqliteHelper.insert(movieId, title_name, isFavourite);
                                }
                            }else {
                                fab.setSelected(false);
                                isFavourite = false;
                                fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favourite_border));
                                sqliteHelper.update(movieId,isFavourite);
                            }

                        }
                    });
                    if(sqliteHelper.itsFavourite(movieId)){
                        fab.setSelected(true);
                        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_favourite));
                    }else {
                        fab.setSelected(false);
                        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_favourite_border));
                    }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ++ ");
    }

}
