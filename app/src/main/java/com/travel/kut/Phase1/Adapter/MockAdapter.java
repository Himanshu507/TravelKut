package com.travel.kut.Phase1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.kut.R;

public class MockAdapter extends RecyclerView.Adapter<MockAdapter.SimpleViewHolder>  {

    private Context mContext;

    public MockAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mockup, parent, false);
        return new MockAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        holder.date.setText(position+" January 2020");
    }

    @Override
    public int getItemCount() {
        return 22;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView date;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);

        }

    }
}
