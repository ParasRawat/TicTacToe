package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //if game starter is player 1, then you are the challenger
        //if game started is player 2, then your are the challenged player
        Log.d(TAG, "getSharedPrefereces "+getSharedPreferences());
        Log.d(TAG, "getGameStarter "+getGameStarter());
        Log.d(TAG, "getGame "+getGame());
        Log.d(TAG, "getSecondPlayer "+getSecondPlayer());

        if(getGameStarter().equals("player1")){
            crosss.setVisibility(View.VISIBLE);
            fillData();
            Responses responses=new Responses("empty","empty","empty","empty","empty","empty","empty","empty","empty","false","true","");
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference.child(getGame()).setValue(responses).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Challenged player will get first turn", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if(getGameStarter().equals("player2")){
            zero.setVisibility(View.VISIBLE);
            fillData();
            Responses responses=new Responses("empty","empty","empty","empty","empty","empty","empty","empty","empty","false","true","");
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference.child(getGame()).setValue(responses).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "You will have first turn", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block11)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){

                            imageView11.setImageResource(R.drawable.cross);
                            updateData(block11);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView11.setImageResource(R.drawable.zero);
                            updateData(block11);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }


            }
        });
        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block12)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView12.setImageResource(R.drawable.cross);
                            updateData(block12);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView12.setImageResource(R.drawable.zero);
                            updateData(block12);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block13)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView13.setImageResource(R.drawable.cross);
                            updateData(block13);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView13.setImageResource(R.drawable.zero);
                            updateData(block13);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block21)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView21.setImageResource(R.drawable.cross);
                            updateData(block21);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView21.setImageResource(R.drawable.zero);
                            updateData(block21);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block22)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView22.setImageResource(R.drawable.cross);
                            updateData(block22);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView22.setImageResource(R.drawable.zero);
                            updateData(block22);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block23)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView23.setImageResource(R.drawable.cross);
                            updateData(block23);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView23.setImageResource(R.drawable.zero);
                            updateData(block23);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block31)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView31.setImageResource(R.drawable.cross);
                            updateData(block31);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView31.setImageResource(R.drawable.zero);
                            updateData(block31);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block32)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView32.setImageResource(R.drawable.cross);
                            updateData(block32);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView32.setImageResource(R.drawable.zero);
                            updateData(block32);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBlockStatus(block33)){
                    if(getGameStarter().equals("player1")){
                        if(checkTurn(player1turn)){
                            imageView33.setImageResource(R.drawable.cross);
                            updateData(block33);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(getGameStarter().equals("player2")){
                        if(checkTurn(player2turn)){
                            imageView33.setImageResource(R.drawable.zero);
                            updateData(block33);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Its not your turn",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Block Already Filled",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    void fillData(){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference.child(getSharedPreferences()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(gamer1);
                gamer1name.setText(gamerProfile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference1.child(getSecondPlayer()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(gamer2);
                gamer2name.setText(gamerProfile.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    boolean checkBlockStatus(final String block){
        final boolean[] allownce = new boolean[1];
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Responses responses=dataSnapshot.getValue(Responses.class);
                Log.d(TAG, "onDataChange: "+dataSnapshot);
                if(block.equals(block11)){
                    if(responses.getBlock11().equals("empty")){
                         //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block12)){
                    if(responses.getBlock12().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block13)){
                    if(responses.getBlock13().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block21)){
                    if(responses.getBlock21().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block22)){
                    if(responses.getBlock22().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block23)){
                    if(responses.getBlock23().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block31)){
                    if(responses.getBlock31().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block32)){
                    if(responses.getBlock32().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
                if(block.equals(block33)){
                    if(responses.getBlock33().equals("empty")){
                        //
                        allownce[0] =true;
                    }
                    else {
                        allownce[0] =false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return allownce[0];
    }
    boolean checkTurn(final String turn){
        final boolean[] allowance = new boolean[1];
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Responses responses=dataSnapshot.getValue(Responses.class);
                if(turn.equals(player1turn)){
                    if(responses.getPlayer1turn().equals("true")){
                        allowance[0] =true;
                    }
                    else {
                        allowance[0] =false;
                    }
                    if(turn.equals(player2turn)){
                        if(responses.getPlayer2turn().equals("true")){
                            allowance[0] =true;
                        }
                        else {
                            allowance[0] =false;
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return allowance[0];
    }

    void updateData(String block){
        //todo Only update the children

        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put(block,"False");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference.child(getGame()).updateChildren(hashMap);


    }


    }

