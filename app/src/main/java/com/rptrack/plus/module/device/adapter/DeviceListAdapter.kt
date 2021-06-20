package com.rptrack.plus.module.device.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.rptrack.plus.R

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/12/2021 11:28 PM.
 */
class DeviceListAdapter(val mContext: Context) : RecyclerView.Adapter<DeviceListAdapter.MyHolder>() {

    var deviceList=ArrayList<String>();

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_list_adapter, viewGroup, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
      //  holder.bind(mContext, deviceList.get(position), position,)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(mContext: Context, model: String, position: Int) {


        }

    }

}