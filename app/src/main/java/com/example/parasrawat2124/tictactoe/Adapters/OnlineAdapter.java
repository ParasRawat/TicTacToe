package com.example.parasrawat2124.tictactoe.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.R;

import java.util.ArrayList;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.viewHolder> {

    ArrayList<String> usernames=new ArrayList<>();
    ArrayList<String> stat=new ArrayList<>();

    public OnlineAdapter(ArrayList<String> un,ArrayList<String> s){
        usernames=un;
        stat=s;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.olist_row,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.name.setText(usernames.get(i));
        viewHolder.status.setText(stat.get(i));
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add effect
            }
        });
    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,status;
        RelativeLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.t_username);
            status=itemView.findViewById(R.id.t_status);
            layout=itemView.findViewById(R.id.lay_row);
        }
    }
}
