package com.rptrack.plus.module.geofence.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;


import com.rptrack.plus.R;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.ArrayList;


public class ShapeTypeAdapter extends RecyclerView.Adapter<ShapeTypeAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    ArrayList<Integer> dataList;
    SelectShape selectShape;
    int row_index = 0;

    public ShapeTypeAdapter(Context context, SelectShape selectShape) {
        inflater = LayoutInflater.from(context);
        mContext = context;

        row_index = Preferences.getPreference_int(mContext, Constant.SELECTED_SHAPE) - 1;
        this.selectShape = selectShape;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_type_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.imageIcon.setImageResource(dataList.get(position));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectShape.onShapeChangeListener(position + 1);
                row_index = position;
                Preferences.setPreference_int(mContext, Constant.SELECTED_SHAPE, position + 1);
                notifyDataSetChanged();
            }
        });
        if (row_index == position) {
            holder.mainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            holder.imageIcon.setColorFilter(mContext.getResources().getColor(R.color.orange));

        } else {
            holder.mainLayout.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            holder.imageIcon.setColorFilter(Color.parseColor("#ffffff"));


        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageIcon;
        LinearLayout mainLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageIcon);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);
        }
    }


    public interface SelectShape {
        void onShapeChangeListener(int shape);
    }

    public void updateListData(ArrayList<Integer> arraylist){
        this.dataList=arraylist;
        notifyDataSetChanged();
    }


}