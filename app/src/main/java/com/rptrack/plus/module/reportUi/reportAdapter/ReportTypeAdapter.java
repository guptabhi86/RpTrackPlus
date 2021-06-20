package com.rptrack.plus.module.reportUi.reportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rptrack.plus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 2/23/2021.
 */
public class ReportTypeAdapter extends RecyclerView.Adapter<ReportTypeAdapter.Holder> implements Filterable {
    Context mContext;
    List<IconItem> data;
    List<IconItem> mFilterList;
    ValueFilter valueFilter;
    ReportTypeSelected reportTypeSelected;


    public ReportTypeAdapter(Context mContaxt, List<IconItem> datumList1, ReportTypeSelected reportTypeSelected) {
        this.mContext = mContaxt;
        mFilterList = datumList1;
        data=datumList1;
        this.reportTypeSelected=reportTypeSelected;
    }



    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ReportTypeAdapter.ValueFilter();
        }
        return valueFilter;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_adapter, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.vehicleNumber.setText(data.get(position).getLabel());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportTypeSelected.seletedReport(data.get(position));
            }
        });

    }




    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getLabel().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(mFilterList.get(i).getLabel());
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            data = (List<IconItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView vehicleNumber;
        LinearLayout mainLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            vehicleNumber=(TextView)itemView.findViewById(R.id.vehicle_number);
            mainLayout=(LinearLayout)itemView.findViewById(R.id.mainLayout);
        }
    }


    public interface ReportTypeSelected{
        void seletedReport(IconItem report);

    }
}



