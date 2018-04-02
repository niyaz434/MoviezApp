package com.example.mohamedniyaz.moviezapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.activity.MainActivity;
import com.example.mohamedniyaz.moviezapp.activity.MovieIdActivity;
import com.example.mohamedniyaz.moviezapp.adapter.MoviesAdapter;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
import com.example.mohamedniyaz.moviezapp.modules.MovieResponse;
import com.example.mohamedniyaz.moviezapp.rest.ApiClient;
import com.example.mohamedniyaz.moviezapp.rest.ApiInterface;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOne extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: Attached");
    }

    public static final String one = "movie_id";

    private static final String TAG = MainActivity.class.getSimpleName();
    private SimpleDraweeView draweeView;
    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    private String backdropPath;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());



        if (API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: OnCreateView");

        View view  = inflater.inflate(R.layout.fragment_one,container,false);

        draweeView = (SimpleDraweeView) view.findViewById(R.id.my_image_view);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getResults(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                //int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "onResponse: ");
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.recycler_view_list, getContext()));



            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


        return view;

    }

}
