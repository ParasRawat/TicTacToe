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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {

    ArrayList<String> reqrec;
    public static String CHALLENGED,CHALLENGER;
    Context context;

    public RequestAdapter(Context context,ArrayList<String> reqrec, String challenged){
        this.reqrec=reqrec;
        CHALLENGED=challenged;
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
                CHALLENGER=viewHolder.username.getText().toString();
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
                     //remove from reqreceived
                     //TODO successlistener
                     final DatabaseReference dbrefu=FirebaseDatabase.getInstance().getReference("Users");
                     dbrefu.child(CHALLENGED).child("reqreceived").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             GenericTypeIndicator<ArrayList<String>> t=new GenericTypeIndicator<ArrayList<String>>() {};
                             ArrayList<String> arrlist=dataSnapshot.getValue(t);
                             arrlist.remove(CHALLENGER);
                             dbrefu.child(CHALLENGED).child("reqreceived").setValue(arrlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     //notifyChallenger(CHALLENGER+"vs"+CHALLENGED);
                                     String matchid=CHALLENGER+"vs"+CHALLENGED;
                                     DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("PlayStatus");
                                     dbref.child(matchid).child("ChallengedStatus").setValue("ready").addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {
                                             Log.d("requestAc",CHALLENGER+CHALLENGED+"accept");
                                             Intent i=new Intent("match");
                                             LocalBroadcastManager.getInstance(context).sendBroadcast(i);
                                         }
                                     });
                                 }
                             });
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                 }
             }
         });
     }

     private void notifyChallenger(String matchid){
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("PlayStatus");
        dbref.child(matchid).child("Challenger").setValue(CHALLENGER);
        dbref.child(matchid).child("Challenged").setValue(CHALLENGED);
        dbref.child(matchid).child("ChallengerStatus").setValue("ready");
        dbref.child(matchid).child("ChallengedStatus").setValue("ready");
     }
}
