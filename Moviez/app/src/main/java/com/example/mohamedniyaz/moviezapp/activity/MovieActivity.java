package com.example.mohamedniyaz.moviezapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.fragments.MovieFragment;
import com.example.mohamedniyaz.moviezapp.fragments.MovieResponseFragment;
import com.example.mohamedniyaz.moviezapp.interfaces.FragmentActivityCommunication;
import com.example.mohamedniyaz.moviezapp.moviezApp.ConstantMethods;

// TODO Reasonable naming
public class MovieActivity extends AppCompatActivity implements FragmentActivityCommunication {
    public static final String TAG = "MainAvtivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstantMethods.newInstance().printLogs("onCreate", "onCreate:  ++ ");
        Fragment fragment = new MovieFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_one_frame, fragment);
            fragmentTransaction.addToBackStack(MovieFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConstantMethods.newInstance().printLogs(TAG, "onResume: ++");
    }

    @Override
    public void movieId(int position) {
        Fragment fragment = MovieResponseFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_one_frame, fragment)
                .addToBackStack(MovieResponseFragment.class.getSimpleName()).commit();
        ConstantMethods.newInstance().printLogs(TAG, "movieId: " + position);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            ConstantMethods.newInstance().printLogs(this.getClass().getSimpleName(), MovieFragment.class.getSimpleName());
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
        ConstantMethods.newInstance().printLogs(TAG, "onBackPressed: " + count);
    }
}
