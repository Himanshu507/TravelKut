package com.travel.kut.Phase1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.kut.R;

import java.util.ArrayList;

public class Main_Places_Adapter extends RecyclerView.Adapter<Main_Places_Adapter.SimpleViewHolder> {

    Context mContext;

    ArrayList<String> urls = new ArrayList<>();
    int a =0;

    public Main_Places_Adapter(Context context) {
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
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.places_item_main, viewGroup, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder simpleViewHolder, int i) {

        if (i<6)
            Glide.with(mContext).load(urls.get(i)).into(simpleViewHolder.place_image);
        else {
            Glide.with(mContext).load(urls.get(a)).into(simpleViewHolder.place_image);
        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public TextView place_name, place_location;
        CardView cardView;
        ImageView place_image;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
                cardView = itemView.findViewById(R.id.place_card);
                place_image = itemView.findViewById(R.id.place_image);
                place_name = itemView.findViewById(R.id.place_name);
                place_location = itemView.findViewById(R.id.place_location);
        }

    }
}