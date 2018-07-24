package com.example.mohamedniyaz.moviezapp.adapter;

import android.content.Context;
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
import com.example.mohamedniyaz.moviezapp.database.SqliteHelper;
import com.example.mohamedniyaz.moviezapp.interfaces.AdapterFragment;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import static android.content.ContentValues.TAG;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private SqliteHelper sqliteHelper;
    private List<Movie> movies;
    private Context context;
    private AdapterFragment adapterFragment;
    //private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    private long mLastClickTime = 0;
    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/" );

    public MoviesAdapter(Context context) {
        this.context = context;
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ToggleButton toggleButton;
        RelativeLayout movieslayout;
        TextView movie_title;
        SimpleDraweeView draweeView;
        TextView rating_textview;


        public MovieViewHolder(View v) {
            super(v);
            movieslayout = (RelativeLayout) v.findViewById(R.id.movies_layout);
            movie_title = (TextView) v.findViewById(R.id.movie_title);
            draweeView= (SimpleDraweeView) v.findViewById(R.id.my_image_view);
            rating_textview = (TextView)v.findViewById(R.id.rating_bar);
            toggleButton = (ToggleButton)v.findViewById(R.id.myToggleButton);

        }
    }

    public MoviesAdapter(List<Movie> movies, int list_item, Context context, AdapterFragment adapterFragment) {
        this.movies = movies;
        this.context = context;
        this.adapterFragment = adapterFragment;
        sqliteHelper = new SqliteHelper(context);

    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list, parent, false);
        return new MovieViewHolder(view);
    }


    public void update(List<Movie> moviesList){
        this.movies = moviesList;
        notifyDataSetChanged();
        Log.d(TAG, "update: ++ ");
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        if(movies.get(position).getFavourite()){
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite));
        }
        else{
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite_border));
        }
//        if(sqliteHelper.itsFavourite(movies.get(position).getId())){
//            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite));
//        }else {
//            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite_border));
//        }

        //TODO use button with selector
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    movies.get(position).setFavourite(false);
                }
                if(movies.get(position).getFavourite()){
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite));
                  if (sqliteHelper.data(movies.get(position).getId())) {
                      sqliteHelper.update(movies.get(position).getId(), movies.get(position).getFavourite());
                  }else {
                      sqliteHelper.insert(movies.get(position).getId(), movies.get(position).getTitle(), movies.get(position).getFavourite());
                  }
                }
                else{
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favourite_border));
                    sqliteHelper.update(movies.get(position).getId(),movies.get(position).getFavourite());
                    System.out.println(isChecked);
                }

            }
        });




        holder.movie_title.setText(movies.get(position).getTitle());
        holder.draweeView.setImageURI(uri + movies.get(position).getBackdropPath());
        holder.rating_textview.setText(movies.get(position).getVoteAverage().toString());
        holder.movieslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime<1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                int id = movies.get(position).getId();
                adapterFragment.onItemClicked(id);
//                Intent intent = new Intent(context,MovieIdActivity.class);
//                //TODO do not use random name, Use constants
//                intent.putExtra("Int",id);
//                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }


}
