package com.travel.kut.Phase1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.kut.R;

import java.util.ArrayList;


public class Hot_Places_Adapter extends RecyclerView.Adapter<Hot_Places_Adapter.SimpleViewHolder>{

    Context mContext;

    ArrayList<String> urls = new ArrayList<>();
    int a =0;

    public Hot_Places_Adapter(Context context) {
        this.mContext = context;
        urls.add("https://www.creativeboom.com/uploads/articles/68/68ef1f34ab4ef76b4e9d844e435e062684933002_1100.jpg");
        urls.add("https://upload.wikimedia.org/wikipedia/commons/d/d4/London_by_night.jpg");
        urls.add("https://static.lakana.com/mmm-global-us-east-1/photo/2019/04/22/55929887_1555921890881_38161017_ver1.0_1280_720.jpg");
        urls.add("https://www.irishtimes.com/polopoly_fs/1.3863344.1555499320!/image/image.jpg");
        urls.add("https://www.designlike.com/wp-content/uploads/2011/12/Scarlet-Night-Sydney-Opera-House-Sydney-Australia.jpg");
        urls.add("https://thearchitecturedesigns.com/wp-content/uploads/2018/11/1.best-museums-1024x683.jpg");

    }

    @NonNull
    @Override
    public Hot_Places_Adapter.SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hot_places_item, viewGroup, false);
        return new Hot_Places_Adapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Hot_Places_Adapter.SimpleViewHolder simpleViewHolder, int i) {

        Glide.with(mContext).load("https://www.treebo.com/blog/wp-content/uploads/2017/12/Beaches-in-India_1.jpg").into(simpleViewHolder.story_picture);
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        ImageView story_picture;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            story_picture = itemView.findViewById(R.id.hot_place_image);

        }

    }
}

