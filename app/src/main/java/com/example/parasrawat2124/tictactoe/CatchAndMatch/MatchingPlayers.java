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

import com.example.parasrawat2124.tictactoe.ModelClass.GamerProfile;
import com.example.parasrawat2124.tictactoe.ModelClass.Matching;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MatchingPlayers extends AppCompatActivity{
    ImageView challengerimage,challengedimage;
    TextView challengerstatus,challengedtatus;
    CardView countdowncard;
    TextView countdowntextview;
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
        countdowntextview=findViewById(R.id.countdowntextview);
        cTimer=null;
        condition();

    }
    void condition(){
        //todo: see if anyone has started the match
        //response 1 means i want to see the requests
        //response 2 means i have invited someone
        if(getResponse().equals("1")){

            //todo Check from the database if any match has been active on in your name, if yes then fill in the challenger and the challenged and await the response
        }
        else if(getResponse().equals("2")){
            //Fill in the credentials in the views;
            String challenegr=getSharedPreferences();
            String challeneged=getSecondPlayer();
            Log.d(TAG, "condition: "+challenegr+"   "+challeneged);
            fillChallengerData(challenegr);
            fillChallenegedData(challeneged);
            //todo await for the player reply
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
                        countdowncard.setVisibility(View.VISIBLE);
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
}
