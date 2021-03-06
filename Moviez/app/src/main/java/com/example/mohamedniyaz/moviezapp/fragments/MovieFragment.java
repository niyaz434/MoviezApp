package com.example.mohamedniyaz.moviezapp.fragments;

//TODO unused imports and variables (Shortcuts)

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.activity.MovieActivity;
import com.example.mohamedniyaz.moviezapp.adapter.MoviesAdapter;
import com.example.mohamedniyaz.moviezapp.database.SqliteHelper;
import com.example.mohamedniyaz.moviezapp.interfaces.AdapterFragment;
import com.example.mohamedniyaz.moviezapp.interfaces.FragmentActivityCommunication;
import com.example.mohamedniyaz.moviezapp.interfaces.HandlerResultListener;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
import com.example.mohamedniyaz.moviezapp.modules.MovieResponse;
import com.example.mohamedniyaz.moviezapp.moviezApp.AppConstants;
import com.example.mohamedniyaz.moviezapp.moviezApp.ConstantMethods;
import com.example.mohamedniyaz.moviezapp.rest.ApiClient;
import com.example.mohamedniyaz.moviezapp.rest.ApiInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    public static final String one = "movie_id";

    private static final String TAG = MovieActivity.class.getSimpleName();
    private SimpleDraweeView draweeView;
    // TODO - insert your themoviedb.org API KEY here
    //private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public static int page = 1;
    private String backdropPath;
    private List<Movie> movies = new ArrayList<>();
    private List<Movie> moviesList = new ArrayList<>();
    RecyclerView recyclerView;
    MoviesAdapter moviesAdapter;
    boolean reachedLastPosition;
    boolean isReloaded = false;
    private SqliteHelper sqliteHelper;
    private FragmentActivityCommunication fragmentActivityCommunication;
    int total = 0;
    int lastVisibleItemCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        //TODO major libraries need to be initialized in application class
        //TODO not required
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: Attached");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO allignment shortcut
        Log.d(TAG, "onCreateView: OnCreateView");

        final View view = inflater.inflate(R.layout.fragment_moviez_list, container, false);

        draweeView = view.findViewById(R.id.my_image_view);
        recyclerView = view.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(moviesList, getActivity(), new AdapterFragment() {
                @Override
                public void onItemClicked(int position) {
                    fragmentActivityCommunication = (FragmentActivityCommunication) getActivity();
                    fragmentActivityCommunication.movieId(position);
                    Log.d(TAG, "onItemClicked: " + position);

                }
            });
        }
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                total = layoutManager.getItemCount();
                //TODO not required
                // int firstVisibleItemCount = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItemCount = layoutManager.findLastVisibleItemPosition();
                if ((!reachedLastPosition) && (total > 0) && ((total - 1) == lastVisibleItemCount)) {
                    page++;
                    loadData();
                    //TODO isLoaded could be a better name
                    reachedLastPosition = true;
                }
            }
        });

        return view;

    }


    public void loadData() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getResults(AppConstants.API_KEY, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                isReloaded = true;
                //int statusCode = response.code();
                movies = response.body().getResults();

                Log.d(TAG, "onResponse: " + page);
                //TODO Not required handle at Entity
                for (int i = 0; i < movies.size(); i++) {
                    ConstantMethods.newInstance().printLogs(MovieFragment.class.getSimpleName(), String.valueOf(movies.get(i)));
                    Log.d(TAG, "MoviesList: " + movies.get(i).getTitle());
                }
                moviesList.addAll(movies);
                if (moviesAdapter != null) {
                    moviesAdapter.update(moviesList);
                }
                reachedLastPosition = false;
                recyclerView.setMotionEventSplittingEnabled(false);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    //TODO Not required to run a loop a select query should do
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume movie fragment ++");
        if (sqliteHelper == null) {
            sqliteHelper = new SqliteHelper(getActivity());
        }
        sqliteHelper.getFavouriteMovie(new HandlerResultListener() {
            @Override
            public void onResult(Object... object) {
                ArrayList<Integer> favouriteMovieId = (ArrayList<Integer>) object[0];
                for (int i = 0; i < moviesList.size(); i++) {
                    for (int j = 0; j < favouriteMovieId.size(); j++) {
                        if (moviesList.get(i).getId().equals(favouriteMovieId.get(j))) {
                            ConstantMethods.newInstance().printLogs(MovieFragment.class.getSimpleName(), "Movie Name " + moviesList.get(i).getTitle() + "Favourite id" + favouriteMovieId.get(j));
                            moviesList.get(i).setFavourite(true);
                            break;
                        } else {
                            moviesList.get(i).setFavourite(false);
                        }
                    }
                }
                if (moviesAdapter != null) {
                    moviesAdapter.update(moviesList);
                }
            }
        });
        Log.d(TAG, "onResume movie fragment --");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: " + moviesList.size());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (moviesList.size() > 0) {
            moviesList.clear();
        }
    }
}
