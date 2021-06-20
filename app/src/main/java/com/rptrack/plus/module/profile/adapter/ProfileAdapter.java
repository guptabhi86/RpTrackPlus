package com.rptrack.plus.module.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.R;
import com.rptrack.plus.module.profile.TItleIcon;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.Holder> {

    Context mContext;
    ArrayList<TItleIcon> stringArrayList;
    ClickedListener clickedListener;

    public ProfileAdapter(Context mContaxt1, ArrayList<TItleIcon> stringArrayList,ClickedListener clickedListener) {
        mContext = mContaxt1;
        this.stringArrayList = stringArrayList;
        this.clickedListener=clickedListener;

    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView icons;
        RelativeLayout mainLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            icons = itemView.findViewById(R.id.icons);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }

    @NonNull
    @Override
    public ProfileAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_adapter, parent, false);
        return new ProfileAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.textTitle.setText(stringArrayList.get(position).getTitle());
        holder.icons.setImageResource(stringArrayList.get(position).getIcon());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedListener.clicekPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();

    }
    public interface ClickedListener{
        void clicekPosition(int position);
    }


}























