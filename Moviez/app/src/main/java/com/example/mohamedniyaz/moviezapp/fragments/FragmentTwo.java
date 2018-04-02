package com.example.mohamedniyaz.moviezapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamedniyaz.moviezapp.R;
import com.example.mohamedniyaz.moviezapp.modules.AdapterModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class FragmentTwo extends Fragment{
    TextView title,description,rating,rating_count,genre;
    SimpleDraweeView fresco_image;


    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/" );
    private final static String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public int movieId;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two,container,false);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingToolbar);


        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //  title = (TextView)findViewById(R.id.title_movie);
        final FloatingActionButton fab;
        final boolean[] flag = {true}; // true if first icon is visible, false if second one is visible.

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag[0]){

                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favourite));
                    flag[0] = false;

                }else if(!flag[0]){

                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favourite_border));
                    flag[0] = true;

                }

            }
        });
        description = (TextView)view.findViewById(R.id.description_text);
        rating  = (TextView)view.findViewById(R.id.rating_text);
        rating_count = (TextView)view.findViewById(R.id.rating_count_text);
        genre = (TextView)view.findViewById(R.id.genre_text);
        fresco_image = (SimpleDraweeView)view.findViewById(R.id.toolbarImage) ;


        Intent intent = getActivity().getIntent();
        ArrayList<AdapterModel> value = new ArrayList<AdapterModel>();
        value = intent.getParcelableArrayListExtra("Array");

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<value.size();i++){

            collapsingToolbarLayout.setTitle(value.get(i).getTitle_name());
            description.setText(value.get(i).getOverview());
            rating.setText(" "+value.get(i).getVote_average());
            rating_count.setText(" "+value.get(i).getVote_count());
            fresco_image.setImageURI(uri + value.get(i).getBackdroppath());


            System.out.println("Nope: "+value.get(i).getOverview().toString());
            System.out.println("Array"+value.get(i).getGenereClasses().toString());
            for(int j = 0;j<value.get(i).getGenereClasses().size();j++){
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(value.get(i).getGenereClasses().get(j).getName().toString());
                System.out.println("String Builder"+stringBuilder.toString());
                System.out.println("ArrayIn "+value.get(i).getGenereClasses().get(j).getName().toString());
            }
            stringBuilder.append('.');
            genre.setText(stringBuilder.toString());

        }


        return view;
    }
}
