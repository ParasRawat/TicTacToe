package com.example.parasrawat2124.tictactoe.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {

    ArrayList<String> reqrec;
    public static String CHALLENGED,CHALLENGER;

    public RequestAdapter(ArrayList<String> reqrec,String challenger){
        this.reqrec=reqrec;
        CHALLENGER=challenger;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reqlist_row,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {
        viewHolder.username.setText(reqrec.get(i));
        CHALLENGED=viewHolder.username.getText().toString();
//        viewHolder.acceptB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CHALLENGED=viewHolder.username.getText().toString();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return reqrec.size();
    }

     public class viewHolder extends RecyclerView.ViewHolder{

        TextView username;
       // Button acceptB;

         public viewHolder(@NonNull View itemView) {
             super(itemView);
             username=itemView.findViewById(R.id.t_username);
            // acceptB=itemView.findViewById(R.id.b_accept);
         }
     }

     public void acceptReq(View view){

     }

}
