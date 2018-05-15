package com.example.mohamedniyaz.moviezapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.fragments.FragmentTwo;
import com.facebook.drawee.view.SimpleDraweeView;

public class MovieIdActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_id);

        Fragment fragmentTwo = new FragmentTwo();
        if(fragmentTwo!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_two_frame, fragmentTwo).commit();
        }




    }




}
