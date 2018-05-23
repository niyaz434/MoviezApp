package com.example.mohamedniyaz.moviezapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.fragments.MovieResponseFragment;

public class MovieIdActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_id);

        Fragment fragmentTwo = new MovieResponseFragment();
        if(fragmentTwo!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_two_frame, fragmentTwo).commit();
        }




    }




}
