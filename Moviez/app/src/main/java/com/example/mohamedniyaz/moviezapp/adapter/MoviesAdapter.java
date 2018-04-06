package com.example.mohamedniyaz.moviezapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.activity.MovieIdActivity;
import com.example.mohamedniyaz.moviezapp.modules.AdapterModel;
import com.example.mohamedniyaz.moviezapp.modules.GenereClass;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
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


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;
    private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/" );


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout movieslayout;
        TextView movie_title;
        SimpleDraweeView draweeView;
        TextView rating_textview;
        ToggleButton toggleButton;


        public MovieViewHolder(View v) {
            super(v);
            movieslayout = (RelativeLayout) v.findViewById(R.id.movies_layout);
            movie_title = (TextView) v.findViewById(R.id.movie_title);
            draweeView= (SimpleDraweeView) v.findViewById(R.id.my_image_view);
            rating_textview = (TextView)v.findViewById(R.id.rating_bar);
            toggleButton = (ToggleButton)v.findViewById(R.id.myToggleButton);
        }
    }

    public MoviesAdapter(List<Movie> movies, int list_item, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: "+position);



        SharedPreferences sharedPreferences = context.getSharedPreferences("boolean",Context.MODE_PRIVATE);
        Boolean a = sharedPreferences.getBoolean("check"+position,false);
        if(a){
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite));
            holder.toggleButton.setChecked(true);
        }else {
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite_border));
            holder.toggleButton.setChecked(false);
        }


                holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if(isChecked){
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite));
                    SharedPreferences.Editor editor = context.getSharedPreferences("boolean",Context.MODE_PRIVATE).edit();
                    editor.putBoolean("check"+position,true);
                    editor.apply();
                }
                else{
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite_border));
                    SharedPreferences.Editor editor = context.getSharedPreferences("boolean",Context.MODE_PRIVATE).edit();
                    editor.putBoolean("check"+position,false);
                    editor.apply();
                }
            }
        });



        holder.movie_title.setText(movies.get(position).getTitle());
        holder.draweeView.setImageURI(uri + movies.get(position).getBackdropPath());
        holder.rating_textview.setText(movies.get(position).getVoteAverage().toString());
        holder.movieslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<MovieId> call = apiService.getMovieDetails(movies.get(position).getId(),API_KEY);
                call.enqueue(new Callback<MovieId>() {
                    @Override
                    public void onResponse(Call<MovieId> call, Response<MovieId> response) {
                        System.out.println(movies.get(position).getId());
                        //int statusCode = response.code();
                       // List<Movie> movies = response.body().getResults();

                       ArrayList<AdapterModel> arrayList = new ArrayList<>();

                        String title_name = response.body().getOriginal_title();
                        Log.d(TAG, "Title name: "+title_name);
                        String overview = response.body().getOverview();
                        float vote_average = response.body().getVote_average();
                        ArrayList<GenereClass> genereClasses = (ArrayList<GenereClass>) response.body().getGenres();
                        ArrayList<SpokenClass> spokenClasses = (ArrayList<SpokenClass>) response.body().getSpoken_languages();
                        int vote_count  = response.body().getVote_count();
                        String backdrop_path = response.body().getBackdropPath();

                        arrayList.add(new AdapterModel(title_name,overview,vote_average,vote_count,genereClasses,backdrop_path,spokenClasses));

                        for(int i =0; i<arrayList.size();i++){
                            System.out.println("value: " + arrayList.get(i).getOverview().toString());

                        }


                        Log.d(TAG, "onResponse: ");
                        Intent intent = new Intent(context,MovieIdActivity.class);
                        intent.putParcelableArrayListExtra("Array",arrayList);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<MovieId> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });

            }
        });
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }



}
