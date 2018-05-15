package com.example.mohamedniyaz.moviezapp.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.fragments.FragmentOne;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Fragment fragment = new FragmentOne();
        if(fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_one_frame, fragment).commit();
        }

    }








}
