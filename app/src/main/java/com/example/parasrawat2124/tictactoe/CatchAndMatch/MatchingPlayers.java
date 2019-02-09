package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.ModelClass.GamerProfile;
import com.example.parasrawat2124.tictactoe.ModelClass.Matching;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MatchingPlayers extends AppCompatActivity{
    ImageView challengerimage,challengedimage;
    TextView challengerstatus,challengedtatus;
    CardView countdowncard;
    TextView countdowntextview;
    CardView readycardview;
    CountDownTimer cTimer ;
    public static final String TAG="Challenge";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_players);
        challengerimage=findViewById(R.id.challengerimage);
        challengedimage=findViewById(R.id.challengedimage);
        challengerstatus=findViewById(R.id.challengerstatus);
        challengedtatus=findViewById(R.id.challenegedstatus);
        countdowncard=findViewById(R.id.countdowncard);
        readycardview=findViewById(R.id.readycardview);
        countdowntextview=findViewById(R.id.countdowntextview);
        cTimer=null;
        condition();
        readycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //challeneged player responses
                readyMatch();
            }
        });


    }
    void condition(){
        //todo: see if anyone has started the match
        //response 1 means i want to see the requests
        //response 2 means i have invited someone
        if(getResponse().equals("1")){
            String name=getSharedPreferences();
            checkMatch(name);
        }
        else if(getResponse().equals("2")){
            //Fill in the credentials in the views;
            String challenegr=getSharedPreferences();
            String challeneged=getSecondPlayer();
            Log.d(TAG, "condition: "+challenegr+"   "+challeneged);
            fillChallengerData(challenegr);
            fillChallenegedData(challeneged);
        }
    }
    String getResponse(){
        SharedPreferences sharedPreferences=getSharedPreferences("Response",Context.MODE_PRIVATE);
        String resposne=sharedPreferences.getString("response","0");
        return resposne;
    }
    String getSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }
    String getSecondPlayer(){
        SharedPreferences sharedPreferences=getSharedPreferences("Player2",MODE_PRIVATE);
        String name=sharedPreferences.getString("Player2Name","0");
        return name;
    }
    void fillChallengerData(String name){
        Log.d(TAG, "fillChallengerData: "+"Called");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "Challenger Data: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(challengerimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Set status
        DatabaseReference statusrefrence=FirebaseDatabase.getInstance().getReference("ActiveMtach");
        statusrefrence.child(getSharedPreferences()+"vs"+getSecondPlayer()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    Log.d(TAG, "Status"+dataSnapshot);
                    Matching matching=dataSnapshot.getValue(Matching.class);
                }
                catch (Exception e){
                    Log.d(TAG, "onDataChange: "+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void fillChallenegedData(String name){
        Log.d(TAG, "fillChallenegedData: "+"Called");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "Challenged Data: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(challengedimage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //set status

        DatabaseReference statusrefrence=FirebaseDatabase.getInstance().getReference("ActiveMtach");
        statusrefrence.child(getSharedPreferences()+"vs"+getSecondPlayer()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    Log.d(TAG, "Status"+dataSnapshot);
                    Matching matching=dataSnapshot.getValue(Matching.class);
                    if(matching.getPlayer2status().equals("notready")){
                        challengedtatus.setText("Waiting for the reply....");
                    }
                    else if(matching.getPlayer2status().equals("ready")){
                        challengedtatus.setText("I am Ready, Lets roll");
                        startTimer();

                    }
                }
                catch (Exception e){
                    Log.d(TAG, "onDataChange: "+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void startTimer() {
        countdowncard.setVisibility(View.VISIBLE);
        cTimer = new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                int mil= (int) millisUntilFinished/1000;
                Log.d(TAG, "onTick: "+mil);
                if(mil==0){
                    countdowntextview.setText("Start");
                }
                else {
                    countdowntextview.setText("" + mil);
                }
            }
            public void onFinish() {
                Log.d(TAG, "onFinish: "+"On Finished Called");
                startActivity(new Intent(MatchingPlayers.this,Match.class));
            }
        };
        cTimer.start();
    }
    void checkMatch(final String name){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ActiveMtach");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()
                     ) {
                    Log.d(TAG, "PLAYER 2 INFO "+data);
                    Matching matching=data.getValue(Matching.class);
                    if(matching.getPlayer2().equals(name)){
                        Toast.makeText(getApplicationContext(),"You have 1 challenge",Toast.LENGTH_LONG).show();
                        fillChallenegedData(name);
                        storeChallengerName(matching.getPlayer1());
                        fillChallengerData(matching.getPlayer1());
                        readycardview.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void readyMatch(){
        String player2=getSharedPreferences();
        String player1=getChallengerName();
        Matching matching=new Matching(player1,player2,"","","","ready","ready");
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("player2status","ready");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ActiveMtach");
        databaseReference.child(player1+"vs"+player2).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    readycardview.setVisibility(View.GONE);
                    startTimer();
                }

            }
        });
    }
    void storeChallengerName(String name){
        SharedPreferences sharedPreferences=getSharedPreferences("Challenger",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit().putString("ChallengerName",name);
        editor.apply();
    }
    String getChallengerName(){
        SharedPreferences sharedPreferences=getSharedPreferences("Challenger",MODE_PRIVATE);
        String name=sharedPreferences.getString("ChallengerName","0");
        return name;
    }

}
