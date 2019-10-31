package com.travel.kut.Phase1.MainFragments;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.travel.kut.Phase1.Adapter.Hot_Places_Adapter;
import com.travel.kut.Phase1.Adapter.Main_Places_Adapter;
import com.travel.kut.Phase1.Adapter.Story_Adapter;
import com.travel.kut.Phase1.Adapter.Top_Journey_Adapter_Main;
import com.travel.kut.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    AppBarLayout main_background_img;
    CircleImageView user_pic;
    RecyclerView places_recycler, stories_recycler,journey_recycler,hot_places_recycler;
    Main_Places_Adapter mainPlacesAdapter;
    Story_Adapter story_adapter;
    Top_Journey_Adapter_Main top_journey_adapter_main;
    Hot_Places_Adapter hot_places_adapter;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        main_background_img = view.findViewById(R.id.appbar_layout);
        user_pic = view.findViewById(R.id.user_picc);
        places_recycler = view.findViewById(R.id.recylcer_places_for_you);
        stories_recycler = view.findViewById(R.id.stories_recycler);
        journey_recycler = view.findViewById(R.id.journey_recycler);
        hot_places_recycler = view.findViewById(R.id.hot_places_recycler);
        hot_places_adapter = new Hot_Places_Adapter(getContext());
        mainPlacesAdapter = new Main_Places_Adapter(getContext());
        story_adapter = new Story_Adapter(getContext());
        places_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        places_recycler.setAdapter(mainPlacesAdapter);

        stories_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        stories_recycler.setAdapter(story_adapter);

        top_journey_adapter_main = new Top_Journey_Adapter_Main(getContext());
        journey_recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        journey_recycler.setAdapter(top_journey_adapter_main);


        hot_places_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        hot_places_recycler.setAdapter(hot_places_adapter);

        Glide.with(this).load("https://cdn2us.denofgeek.com/sites/denofgeekus/files/styles/main_wide/public/2019/03/detective-pikachu-pokemon-movie-ryan-reynolds.jpg?itok=Pxd56lut").into(user_pic);

        Glide.with(this).load("https://www.women-traveling.com/DesktopModules/TripView/TripImage.ashx?TripID=2020-0028").into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    main_background_img.setBackground(resource);
                }
            }
        });
        //Glide.with(getContext()).load("https://www.women-traveling.com/DesktopModules/TripView/TripImage.ashx?TripID=2020-0028").into(main_background_img);
        return view;
    }

}
