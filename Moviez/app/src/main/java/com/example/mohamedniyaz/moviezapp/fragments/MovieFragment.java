package com.example.mohamedniyaz.moviezapp.fragments;

//TODO unused imports and variables (Shortcuts)

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.activity.MovieActivity;
import com.example.mohamedniyaz.moviezapp.adapter.MoviesAdapter;
import com.example.mohamedniyaz.moviezapp.database.SqliteHelper;
import com.example.mohamedniyaz.moviezapp.interfaces.AdapterFragment;
import com.example.mohamedniyaz.moviezapp.interfaces.FragmentActivityCommunication;
import com.example.mohamedniyaz.moviezapp.interfaces.HandlerResultListener;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
import com.example.mohamedniyaz.moviezapp.moviezApp.ConstantMethods;
import com.example.mohamedniyaz.moviezapp.viewModel.MoviesViewModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    public static final String one = "movie_id";

    private static final String TAG = MovieActivity.class.getSimpleName();
    private SimpleDraweeView draweeView;
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
    private MoviesViewModel moviesViewModel;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* moviesViewModel.getmMovieArrayList().observe(getActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {
                    isReloaded = true;
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
                } else {
                    // do nothing
                }
            }
        });    }*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        loadMovies();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: Attached");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                // int firstVisibleItemCount = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItemCount = layoutManager.findLastVisibleItemPosition();
                if ((!reachedLastPosition) && (total > 0) && ((total - 1) == lastVisibleItemCount)) {
                    page++;
                    loadMovies();
//                    loadData();
                    reachedLastPosition = true;
                }
            }
        });

        return view;

    }

    //load data should call the movie repository class and then update the UI initially
    public void loadMovies() {
        moviesViewModel.fetchRecentMoviesApi(page);
    }

//    public void loadData() {
//        final ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//        Call<MovieResponse> call = apiService.getResults(AppConstants.API_KEY, page);
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                isReloaded = true;
//                //int statusCode = response.code();
//                movies = response.body().getResults();
//
//                Log.d(TAG, "onResponse: " + page);
//                for (int i = 0; i < movies.size(); i++) {
//                    ConstantMethods.newInstance().printLogs(MovieFragment.class.getSimpleName(), String.valueOf(movies.get(i)));
//                    Log.d(TAG, "MoviesList: " + movies.get(i).getTitle());
//                }
//                moviesList.addAll(movies);
//                if (moviesAdapter != null) {
//                    moviesAdapter.update(moviesList);
//                }
//                reachedLastPosition = false;
//                recyclerView.setMotionEventSplittingEnabled(false);
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure: ");
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
//    }

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
        moviesViewModel.getMovieArrayList().observe(getActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {
                    isReloaded = true;
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
                } else {
                    // do nothing
                }
            }
        });
        Log.d(TAG, "onResume movie fragment --");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + moviesList.size());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (moviesList.size() > 0) {
            moviesList.clear();
        }
    }
}
