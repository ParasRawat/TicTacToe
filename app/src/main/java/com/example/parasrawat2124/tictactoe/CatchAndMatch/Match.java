package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.SharedPreferences;
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
import com.example.parasrawat2124.tictactoe.ModelClass.Responses;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Match extends AppCompatActivity {
    public static final String TAG="DRAG";
    public static final String IMAGEVIEW_TAG="icon_bitmap";
    ImageView crosss;
    ImageView zero;
    CardView gettingreadycardview;
    String block11="block11";
    String block12="block12";
    String block13="block13";
    String block21="block21";
    String block22="block22";
    String block23="block23";
    String block31="block31";
    String block32="block32";
    String block33="block33";
    String player1turn="player1turn";
    String player2turn="player2turn";
    ImageView imageView11,imageView12,imageView13,imageView21,imageView22,imageView23,imageView31,imageView32,imageView33;
    //todo Player 1 is always the challenger player, player 2 is always the challenged player
    CircleImageView gamer1,gamer2;
    TextView gamer1name,gamer2name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        crosss=findViewById(R.id.cross);
        zero=findViewById(R.id.zero);
        gamer1=findViewById(R.id.gamer1);
        gamer2=findViewById(R.id.gamer2);
        gamer1name=findViewById(R.id.gamer1name);
        gamer2name=findViewById(R.id.gamer2name);
        imageView11=findViewById(R.id.text11);
        imageView12=findViewById(R.id.text12);
        imageView13=findViewById(R.id.text13);
        imageView21=findViewById(R.id.text21);
        imageView22=findViewById(R.id.text22);
        imageView23=findViewById(R.id.text23);
        imageView31=findViewById(R.id.text31);
        imageView32=findViewById(R.id.text32);
        imageView33=findViewById(R.id.text33);
        crosss.setTag(IMAGEVIEW_TAG);
        zero.setTag(IMAGEVIEW_TAG);
        gettingreadycardview=findViewById(R.id.gettingreadycardview);

        //if game starter is player 1, then you are the challenger
        //if game started is player 2, then your are the challenged player
        Log.d(TAG, "getSharedPrefereces "+getSharedPreferences());
        Log.d(TAG, "getGameStarter "+getGameStarter());
        Log.d(TAG, "getGame "+getGame());
        Log.d(TAG, "getSecondPlayer "+getSecondPlayer());


        if(getGameStarter().equals("player1")){
            crosss.setVisibility(View.VISIBLE);
            Responses responses=new Responses("empty","empty","empty","empty","empty","empty","empty","empty","empty","false","true","","player2");
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference.child(getGame()).setValue(responses).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Challenged player will get first turn", Toast.LENGTH_LONG).show();
                        gettingreadycardview.setVisibility(View.GONE);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to start the match", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
        if(getGameStarter().equals("player2")){
            zero.setVisibility(View.VISIBLE);
            Responses responses=new Responses("empty","empty","empty","empty","empty","empty","empty","empty","empty","false","true","","player2");
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference.child(getGame()).setValue(responses).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "You will have first turn", Toast.LENGTH_LONG).show();
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Unable to start the match", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }



    String getSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }


    String getGameStarter(){
        SharedPreferences sharedPreferences=getSharedPreferences("Starter",MODE_PRIVATE);
        String stater=sharedPreferences.getString("GameStarter","0");
        return stater;
    }

    String getGame(){
        SharedPreferences sharedPreferences=getSharedPreferences("Game",MODE_PRIVATE);
        String  game =sharedPreferences.getString("GameName","0");
        return game;
    }

    String getSecondPlayer(){
        SharedPreferences sharedPreferences=getSharedPreferences("SecondPlayer",MODE_PRIVATE);
       String name= sharedPreferences.getString("SecondPlayerName","0");
       return name;
    }

    }

