package com.example.parasrawat2124.tictactoe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.CatchAndMatch.Match;
import com.example.parasrawat2124.tictactoe.ModelClass.DummyMatchModel;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {

    ArrayList<String> reqrec;
    public static String CHALLENGED,CHALLENGER;
    Context context;

    public RequestAdapter(Context context,ArrayList<String> reqrec, String challenger){
        this.reqrec=reqrec;
        CHALLENGER=challenger;
        this.context=context;
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
        viewHolder.acceptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHALLENGED=viewHolder.username.getText().toString();
                SharedPreferences sharedPreferences=context.getSharedPreferences("Match",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("challenger",CHALLENGER);
                editor.putString("challenged",CHALLENGED);
                editor.putString("id",CHALLENGER+"vs"+CHALLENGED);
                editor.apply();

                //PARAS RAWAT FUNCTION
                uploadMatchToDatabase(CHALLENGER+"vs"+CHALLENGED);

            }
        });
    }

    @Override
    public int getItemCount() {
        return reqrec.size();
    }

     public class viewHolder extends RecyclerView.ViewHolder{

        TextView username;
        Button acceptB;

         public viewHolder(@NonNull View itemView) {
             super(itemView);
             username=itemView.findViewById(R.id.t_username);
             acceptB=itemView.findViewById(R.id.b_accept);
         }
     }
     private void uploadMatchToDatabase(String matchid){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
        //FILLING THE EMPTY GRID
         ArrayList<ArrayList<Integer>> grid=new ArrayList<>();
         for(int i=0;i<3;i++){
             ArrayList<Integer> arr=new ArrayList<>();
             for(int j=0;j<3;j++){
                 arr.add(0);
             }
             grid.add(arr);

         }

         DummyMatchModel dummyMatchModel=new DummyMatchModel(grid,"challenged");
         databaseReference.child(matchid).setValue(dummyMatchModel).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Intent i=new Intent("match");
                     LocalBroadcastManager.getInstance(context).sendBroadcast(i);
                 }
             }
         });
     }
}
