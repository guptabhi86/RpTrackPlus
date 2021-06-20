package com.rptrack.plus.bluetooth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DeviceMessageAdapter extends RecyclerView.Adapter<DeviceMessageAdapter.Holder> {

    ArrayList<StringObject> deviceMessage=new ArrayList<>();
    Context mContext;


    public DeviceMessageAdapter(Context context, ArrayList<StringObject> data) {
        mContext=context;
        deviceMessage=data;
           }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.device_message_adapter,parent,false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.messge1.setText(deviceMessage.get(position).getMessage());

        if(deviceMessage.get(position).getMessageType()==1){
            holder.messge1.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        }else if (deviceMessage.get(position).getMessageType()==2){
            holder.messge1.setTextColor(mContext.getResources().getColor(R.color.colorGreen));

        }else {
            holder.messge1.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        Log.d(TAG, "onBindViewHolder: "+deviceMessage.get(position));


    }

    @Override
    public int getItemCount() {
        return deviceMessage.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView messge1;

        public Holder(@NonNull View itemView) {
            super(itemView);
            messge1=(TextView)itemView.findViewById(R.id.txtMessge);

        }
    }
    
   public void refreshData(ArrayList<StringObject> deviceMessage1){
       deviceMessage=deviceMessage1;
       notifyDataSetChanged();
    }
}
