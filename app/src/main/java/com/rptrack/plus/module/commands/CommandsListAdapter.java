package com.rptrack.plus.module.commands;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.R;


public class CommandsListAdapter extends RecyclerView.Adapter<CommandsListAdapter.Holder> {

    Context mContext;
    String[] commandLists;
    commandListener commandListener;


    public CommandsListAdapter(Context mContext, String[] commandLists, commandListener commandListener) {
        this.mContext = mContext;
        this.commandLists = commandLists;
        this.commandListener = commandListener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView commandLabel;
        RelativeLayout mainLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            commandLabel = itemView.findViewById(R.id.command_label);
            mainLayout = itemView.findViewById(R.id.rl_alert_option_settings);
        }
    }

    @NonNull
    @Override
    public CommandsListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.command_adapter, parent, false);
        return new CommandsListAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.commandLabel.setText(commandLists[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commandListener.commandd(commandLists[position], position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commandLists.length;
    }


    public interface commandListener {

        void commandd(String label, int position);

    }


}























