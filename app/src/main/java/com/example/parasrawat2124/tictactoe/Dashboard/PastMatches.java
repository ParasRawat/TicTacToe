package com.example.parasrawat2124.tictactoe.Dashboard;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.parasrawat2124.tictactoe.ModelClass.Responses;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PastMatches extends AppCompatActivity {

    DatabaseReference dbref;
    String currplayer;
    ArrayList<String> pastmatches=new ArrayList<>();
    ListView matchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_matches);

        currplayer=getSharedPreferences();
        Log.d("currplayer",currplayer);
        matchlist=findViewById(R.id.matches);

        //pastmatches.add("past matches");
        dbref=FirebaseDatabase.getInstance().getReference("MatchProcess");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Responses response=snap.getValue(Responses.class);
                    Log.d("response",response.getPlayer2turn().toString());
                    if(response.getPlayer1().equals(currplayer) || response.getPlayer2().equals(currplayer)){
                        pastmatches.add("\n"+snap.getKey()+
                                "\nChallenger : "+response.getPlayer1()
                                +"\nChallenged : "+response.getPlayer2()
                                +"\nWinner : "
                                +"\n");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pastmatches);
        matchlist.setAdapter(adapter);
    }

    String getSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }
}
