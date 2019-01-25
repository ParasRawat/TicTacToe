package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.R;

public class MatchingPlayers extends AppCompatActivity {
    ImageView challengerimage,challengedimage;
    TextView challengerstatus,challengedtatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_players);
        challengerimage=findViewById(R.id.challengerimage);
        challengedimage=findViewById(R.id.challengedimage);
        challengerstatus=findViewById(R.id.challengerstatus);
        challengedtatus=findViewById(R.id.challenegedstatus);
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
            //todo await for the player reply
        }


    }
    String getResponse(){
        SharedPreferences sharedPreferences=getSharedPreferences("Response",Context.MODE_PRIVATE);
        String resposne=sharedPreferences.getString("response","0");
        return resposne;

    }
}
