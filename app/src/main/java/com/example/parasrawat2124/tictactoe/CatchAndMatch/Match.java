package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.Dashboard.Dashboard;
import com.example.parasrawat2124.tictactoe.ModelClass.GamerProfile;
import com.example.parasrawat2124.tictactoe.ModelClass.Responses;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Match extends AppCompatActivity {
    String gravity;
    String newton;
    CircleImageView winnerimage;
    TextView winnername;
    CircleImageView power;
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
    CardView winnercardview;
    TextView powerinfo;
    ImageView imageView11,imageView12,imageView13,imageView21,imageView22,imageView23,imageView31,imageView32,imageView33;
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
        winnername=findViewById(R.id.winner);
        winnerimage=findViewById(R.id.winnerimage);
        imageView32=findViewById(R.id.text32);
        imageView33=findViewById(R.id.text33);
        crosss.setTag(IMAGEVIEW_TAG);
        zero.setTag(IMAGEVIEW_TAG);
        gettingreadycardview=findViewById(R.id.gettingreadycardview);
        winnercardview=findViewById(R.id.winnercardview);
        power=findViewById(R.id.power);
        powerinfo=findViewById(R.id.powerinfo);
        //if game starter is player 1, then you are the challenger
        //if game started is player 2, then your are the challenged player
        Log.d(TAG, "getSharedPrefereces "+getSharedPreferences());
        Log.d(TAG, "getGameStarter "+getGameStarter());
        Log.d(TAG, "getGame "+getGame());
        Log.d(TAG, "getSecondPlayer "+getSecondPlayer());

        //Setting not your turn logic
        gettingreadycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Not your Turn",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: "+"GETTING READY CARD VIEW IS CLICKED");

            }
        });



        //IDENTIFICATION OF PLAYER LOGISTICS
        if(getGameStarter().equals("player1")){
            //pushing the game
            //You started the game as player 1.
            crosss.setVisibility(View.VISIBLE);
            Responses responses=new Responses("empty","empty","empty","empty","empty","empty","empty","empty","empty","false","true","","player2",getSharedPreferences(),getSecondPlayer());
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference.child(getGame()).setValue(responses).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Challenged player will get first turn", Toast.LENGTH_LONG).show();
                        gettingreadycardview.setVisibility(View.VISIBLE);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to start the match", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
        if(getGameStarter().equals("player2")){
            //you started the game as player 2, challenged player
            //pushing the game
            zero.setVisibility(View.VISIBLE);
            Responses responses=new Responses("empty","empty","empty","empty","empty","empty","empty","empty","empty","false","true","","player2",getSharedPreferences(),getSecondPlayer());
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




        //Fill Credentials
        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()
                     ) {
                    Log.d(TAG, "onDataChange: "+data);
                    GamerProfile gamerProfile=data.getValue(GamerProfile.class);
                    if(gamerProfile.getName().equals(getSharedPreferences())){
                        gamer1name.setText(gamerProfile.getName());
                        Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(gamer1);


                    }

                    if(gamerProfile.getName().equals(getSecondPlayer())){
                        gamer2name.setText(gamerProfile.getName());
                        Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(gamer2);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Updater Logic and Gravity Logic and Newton;

        //LOGIC FOR GRAVITY
        DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference("MatchProcess").child(getGame()).child("gravity");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "DATASNAP VALUE FOR GRAVITY: " + dataSnapshot.getValue());
                try {
                    if (dataSnapshot.getValue().toString().equals("true")) {
                        Toast.makeText(getApplicationContext(), "Gravity Successfully  Activated", Toast.LENGTH_SHORT).show();
                        gravity="true";
                        power.setImageResource(R.drawable.overwrite);
                    }
                }
                catch (Exception e){
                    Log.d(TAG, "onDataChange: "+" NULL POINTER EXCEPTION");
                    gravity="false";
                    power.setImageResource(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //LOGIC FOR NEWTON
        DatabaseReference databaseReference4=FirebaseDatabase.getInstance().getReference("MatchProcess").child(getGame()).child("newton");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "DATASNAP VALUE FOR NEWTON: " + dataSnapshot.getValue());
                try {
                    if (dataSnapshot.getValue().toString().equals("true")) {
                        Toast.makeText(getApplicationContext(), "You have newton power, Select the block you wish to swap", Toast.LENGTH_SHORT).show();
                        newton="true";
                        SelectBlock();
                        power.setImageResource(R.drawable.gravity);

                    }
                }
                catch (Exception e){
                    Log.d(TAG, "onDataChange: "+" NULL POINTER EXCEPTION");
                    newton="false";
                    power.setImageResource(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        final DatabaseReference updater=FirebaseDatabase.getInstance().getReference("MatchProcess");
        updater.child(getGame()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "UPDATER CALLING SYSTEM "+"Updater is called");
                Responses responses=dataSnapshot.getValue(Responses.class);
                SetSystemNormal(responses);
                if(!responses.getBlock11().equals("empty")){
                    if(responses.getBlock11().equals("zero")){
                        imageView11.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView11.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }

                if(!responses.getBlock12().equals("empty")){
                    if(responses.getBlock12().equals("zero")){
                        imageView12.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView12.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }

                if(!responses.getBlock13().equals("empty")){
                    if(responses.getBlock13().equals("zero")){
                        imageView13.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView13.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }
                if(!responses.getBlock21().equals("empty")){
                    if(responses.getBlock21().equals("zero")){
                        imageView21.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView21.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }
                if(!responses.getBlock22().equals("empty")){
                    if(responses.getBlock22().equals("zero")){
                        imageView22.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView22.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }
                if(!responses.getBlock23().equals("empty")){
                    if(responses.getBlock23().equals("zero")){
                        imageView23.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView23.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }
                if(!responses.getBlock31().equals("empty")){
                    if(responses.getBlock31().equals("zero")){
                        imageView31.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView31.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }
                if(!responses.getBlock32().equals("empty")){
                    if(responses.getBlock32().equals("zero")){
                        imageView32.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView32.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }

                }
                if(!responses.getBlock33().equals("empty")){
                    if(responses.getBlock33().equals("zero")){
                        imageView33.setImageResource(R.drawable.zero);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    else {
                        imageView33.setImageResource(R.drawable.cross);
                        gettingreadycardview.setVisibility(View.GONE);
                    }
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

















        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
        //ImageView 11
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Checking Newton;


                Log.d(TAG, "NEWTON AT IMAGE 11  "+newton);
                    if(newton.equals("true")){
                        databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "DATA CHANGE FOR 11" + dataSnapshot);
                                Responses responses = dataSnapshot.getValue(Responses.class);
                                //check turn;
                                if (getGameStarter().equals(responses.getTurn())) {
                                    if (getGameStarter().equals("player1")) {
                                        //check if imageview11 has zero;
                                        if(responses.getBlock11().equals("zero")){
                                            //make the moove and push to database and down the newton;
                                            imageView11.setImageResource(R.drawable.cross);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","cross");
                                            hashMap.put("turn","player2");
                                            swapForPlayer1();
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        DownNewton();
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        return;
                                                    }
                                                }
                                            });


                                        }
                                        if(responses.getBlock11().equals("cross")){
                                            //Deny the moove
                                            Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                        }
                                        if(responses.getBlock11().equals("empty")){
                                            //Simply put cross there
                                            imageView11.setImageResource(R.drawable.cross);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","cross");
                                            hashMap.put("turn","player2");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownNewton();
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });
                                        }


                                    } else if (getGameStarter().equals("player2")) {


                                        if(responses.getBlock11().equals("cross")){
                                            //make the moove
                                            imageView11.setImageResource(R.drawable.zero);
                                            HashMap<String , Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","zero");
                                            hashMap.put("turn","player2");
                                            swapForPlayer2();
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownNewton();
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });

                                        }
                                        if(responses.getBlock11().equals("zero")){
                                            //deny the moove
                                        }
                                        if(responses.getBlock11().equals("empty")){
                                            //Simply put zero there
                                            imageView11.setImageResource(R.drawable.zero);
                                            HashMap<String , Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","zero");
                                            hashMap.put("turn","player2");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownNewton();
                                                        if(LuckyNumer()==1){

                                                            PushGravity();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });
                                        }

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                Log.d(TAG, "GRAVITY AT IMAGE 11  "+gravity);
                    if(gravity.equals("true")){
                        databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "DATA CHANGE FOR 11"+dataSnapshot);
                                Responses responses=dataSnapshot.getValue(Responses.class);
                                //checking the turn
                                if(getGameStarter().equals(responses.getTurn())){
                                        //check to allow the user to make change
                                        if(getGameStarter().equals("player1")){
                                            imageView11.setImageResource(R.drawable.cross);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","cross");
                                            hashMap.put("turn","player2");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownGravity();
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });


                                        }
                                        else if(getGameStarter().equals("player2")){
                                            imageView11.setImageResource(R.drawable.zero);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","zero");
                                            hashMap.put("turn","player1");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownGravity();
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });
                                    }


                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 11 "+newton+"  "+gravity);
                    if(gravity.equals("false") && newton.equals("false")) {
                        databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "DATA CHANGE AT 11 "+dataSnapshot);
                                Responses responses=dataSnapshot.getValue(Responses.class);
                                //checking the turn
                                if(getGameStarter().equals(responses.getTurn())){
                                    //todo Check for the gravity value here
                                    if(responses.getBlock11().equals("empty")){
                                        //check to allow the user to make change
                                        if(getGameStarter().equals("player1")){
                                            imageView11.setImageResource(R.drawable.cross);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","cross");
                                            hashMap.put("turn","player2");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });


                                        }
                                        else if(getGameStarter().equals("player2")){
                                            imageView11.setImageResource(R.drawable.zero);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block11","zero");
                                            hashMap.put("turn","player1");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
            }
        });







        //ImageView 12

        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 12  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 12" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock12().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView12.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock12().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock12().equals("empty")){
                                        //Simply put cross there
                                        imageView12.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock12().equals("cross")){
                                        //make the moove
                                        imageView12.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock12().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock12().equals("empty")){
                                        //Simply put zero there
                                        imageView12.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 12 "+gravity);
                    if(gravity.equals("true")){

                        databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "SNAPSHOT AT 12 "+dataSnapshot);
                                Responses responses=dataSnapshot.getValue(Responses.class);
                                if(getGameStarter().equals(responses.getTurn())){
                                        //todo allow the player to make the change
                                        if(getGameStarter().equals("player1")){
                                            imageView12.setImageResource(R.drawable.cross);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block12","cross");
                                            hashMap.put("turn","player2");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownGravity();
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }

                                                        return;
                                                    }
                                                }
                                            });

                                        }
                                        else if(getGameStarter().equals("player2")){
                                            imageView12.setImageResource(R.drawable.zero);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block12","zero");
                                            hashMap.put("turn","player1");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                        DownGravity();
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }

                                                        return;
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                        }



                                }
                                else {
                                    //todo not the players turn; so wait for the reply of other player
                                    gettingreadycardview.setVisibility(View.GONE);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 12 "+newton+"  "+gravity);

                    if(gravity.equals("false") && newton.equals("false")) {
                        databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                                Responses responses=dataSnapshot.getValue(Responses.class);
                                if(getGameStarter().equals(responses.getTurn())){

                                    if(responses.getBlock12().equals("empty")){
                                        //todo allow the player to make the change
                                        if(getGameStarter().equals("player1")){
                                            imageView12.setImageResource(R.drawable.cross);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block12","cross");
                                            hashMap.put("turn","player2");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });

                                        }
                                        else if(getGameStarter().equals("player2")){
                                            imageView12.setImageResource(R.drawable.zero);
                                            HashMap<String,Object> hashMap=new HashMap<>();
                                            hashMap.put("block12","zero");
                                            hashMap.put("turn","player1");
                                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                        gettingreadycardview.setVisibility(View.VISIBLE);
                                                        Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                        if(LuckyNumer()==1){
                                                            PushGravity();
                                                        }
                                                        if(LuckyNumer()==2){
                                                            PushNewton();
                                                        }
                                                        return;
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }
                                else {
                                    //todo not the players turn; so wait for the reply of other player
                                    gettingreadycardview.setVisibility(View.GONE);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


            }
        });













        //ImageView 13
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(TAG, "NEWTON AT IMAGE 13  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 12" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock13().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView13.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock13().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock13().equals("empty")){
                                        //Simply put cross there
                                        imageView13.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock13().equals("cross")){
                                        //make the moove
                                        imageView13.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock13().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock13().equals("empty")){
                                        //Simply put zero there
                                        imageView13.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 13 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 13 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView13.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block13","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView13.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block13","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 13 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock13().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView13.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView13.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });









        //ImageView 21
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 21  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 21" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock21().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView21.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock21().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock21().equals("empty")){
                                        //Simply put cross there
                                        imageView21.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock21().equals("cross")){
                                        //make the moove
                                        imageView21.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock21().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock21().equals("empty")){
                                        //Simply put zero there
                                        imageView21.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 21 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 21 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView21.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block21","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView21.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block21","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 21 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock21().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView21.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView21.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });



        //ImageView 22

        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 22  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 21" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock22().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView22.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock22().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock22().equals("empty")){
                                        //Simply put cross there
                                        imageView22.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock22().equals("cross")){
                                        //make the moove
                                        imageView22.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock22().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock22().equals("empty")){
                                        //Simply put zero there
                                        imageView22.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 22 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 12 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView22.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block22","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView22.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block22","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 22 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock22().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView22.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView22.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });




        //ImageView 23

        imageView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 23  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 23" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock23().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView23.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock23().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock23().equals("empty")){
                                        //Simply put cross there
                                        imageView23.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock23().equals("cross")){
                                        //make the moove
                                        imageView23.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock23().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock23().equals("empty")){
                                        //Simply put zero there
                                        imageView23.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 23 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 23 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView23.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block23","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView23.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block23","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 23 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock23().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView23.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView23.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });



        //ImageView 31

        imageView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 31  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 31" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock31().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView31.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock31().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock31().equals("empty")){
                                        //Simply put cross there
                                        imageView31.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock31().equals("cross")){
                                        //make the moove
                                        imageView31.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock31().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock31().equals("empty")){
                                        //Simply put zero there
                                        imageView31.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 31 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 31 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView31.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block31","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView31.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block31","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 31 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock31().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView31.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView31.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });



        //ImageView 32
        imageView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 32  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 32" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock32().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView32.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock32().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock32().equals("empty")){
                                        //Simply put cross there
                                        imageView32.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock32().equals("cross")){
                                        //make the moove
                                        imageView32.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock32().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock32().equals("empty")){
                                        //Simply put zero there
                                        imageView32.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 32 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 32 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView32.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block32","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView32.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block32","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 23 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock32().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView32.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView32.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });




        //ImageView33
        imageView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 33  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 33" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock33().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView33.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock33().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock33().equals("empty")){
                                        //Simply put cross there
                                        imageView33.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock33().equals("cross")){
                                        //make the moove
                                        imageView33.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock33().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock33().equals("empty")){
                                        //Simply put zero there
                                        imageView33.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 33 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 23 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView33.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block33","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView33.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block33","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 23 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock33().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView33.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView33.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });


        //Winner Logic
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference1.child(getGame()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Responses responses = dataSnapshot.getValue(Responses.class);
                block11 = responses.getBlock11();
                block12 = responses.getBlock12();
                block13 = responses.getBlock13();
                block21 = responses.getBlock21();
                block22 = responses.getBlock22();
                block23 = responses.getBlock23();
                block31 = responses.getBlock31();
                block32 = responses.getBlock32();
                block33 = responses.getBlock33();

                if(block11.equals(block12) && block12.equals(block13) && !block11.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView11);
                    blink(imageView12);
                    blink(imageView13);
                    if(block11.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block11.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }

                if(block21.equals(block22) && block22.equals(block23) && !block21.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView21);
                    blink(imageView22);
                    blink(imageView23);
                    if(block21.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block21.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }
                if(block31.equals(block32) && block32.equals(block33) && !block31.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView31);
                    blink(imageView32);
                    blink(imageView33);

                    if(block31.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block31.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }

                if(block11.equals(block21) && block21.equals(block31) &&  !block11.equals("empty") ){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView11);
                    blink(imageView21);
                    blink(imageView31);

                    if(block11.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block11.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }
                if(block12.equals(block22) && block22.equals(block32) && !block12.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView12);
                    blink(imageView22);
                    blink(imageView32);

                    if(block12.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block12.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }

                if(block13.equals(block23) && block23.equals(block33)  && !block13.equals("empty")) {
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView13);
                    blink(imageView23);
                    blink(imageView33);

                    if(block13.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block13.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }


                if(block11.equals(block22) && block22.equals(block33)  && !block11.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView11);
                    blink(imageView22);
                    blink(imageView33);

                    if(block11.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block11.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }

                if(block13.equals(block22) && block22.equals(block31)  && !block13.equals("empty")){

                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    blink(imageView13);
                    blink(imageView22);
                    blink(imageView31);

                    if(block13.equals("cross")){
                        updatewinner("cross");
                    }
                    if(block13.equals("zero")){
                        updatewinner("zero");
                    }
                    winnercardview.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    //Blink Player
    private void blink(final View v) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (v.getVisibility() == View.VISIBLE) {
                            v.setVisibility(View.INVISIBLE);
                        } else {
                            v.setVisibility(View.VISIBLE);
                        }
                        blink(v);
                    }
                });
            }
        }).start();
    }


    @Override
    public void onBackPressed() {

    }
    // Lucky number logistics

    int LuckyNumer(){
        Random random=new Random();
        int num=random.nextInt(20);
        Log.d(TAG, "LUCKY NUMBER: "+num);
        if(num<5){
            return 1;
        }
        else if(num>5 && num<=10){
            return 2;
        }
        else {
            return 0;
        }
    }



    void PushGravity(){
        HashMap<String ,Object> hashMap=new HashMap<>();
        hashMap.put("gravity","true");
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: "+"gravity pushed");
                }
            }
        });



    }

    void DownGravity(){
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference1.child(getGame()).child("gravity").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: "+"GRAVITY DOWN");
                }
            }
        });


    }


    void updatewinner(String sign){


        Log.d(TAG, "updatewinner: "+sign);

        if(sign.equals("cross")){
            //Player 1 or the challenger has won the game

            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Gamers");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()
                            ) {
                        Log.d(TAG, "onDataChange: "+data);
                        GamerProfile gamerProfile=data.getValue(GamerProfile.class);
                        if(gamerProfile.getName().equals(getSharedPreferences())){
                            winnername.setText(gamerProfile.getName());
                            Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(winnerimage);


                        }
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        if(sign.equals("zero")){

            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Gamers");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()
                            ) {
                        Log.d(TAG, "onDataChange: "+data);
                        GamerProfile gamerProfile=data.getValue(GamerProfile.class);

                        if(gamerProfile.getName().equals(getSecondPlayer())){
                            winnername.setText(gamerProfile.getName());
                            Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(winnerimage);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    //Pushing newton
    void PushNewton(){
        HashMap<String ,Object> hashMap=new HashMap<>();
        hashMap.put("newton","true");
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: "+"Newton Pushed");
                }
            }
        });

    }

    //Pushing down

    void DownNewton(){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseReference.child(getGame()).child("newton").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: "+"Newton is down");
                }
            }
        });
    }

    void SelectBlock(){
        Log.d(TAG, "SelectBlock: "+"+++++++=CALLING SELECT BLOCK +++++++++");
//        final DatabaseReference updater=FirebaseDatabase.getInstance().getReference("MatchProcess");
//        updater.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG, "UPDATER CALLING SYSTEM "+"+++++++=CALLING SELECT BLOCK +++++++++");
//                Responses responses=dataSnapshot.getValue(Responses.class);
//                ModifySystem(responses);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        DatabaseReference databaseRefe=FirebaseDatabase.getInstance().getReference("MatchProcess");
        databaseRefe.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: "+dataSnapshot);
                if(dataSnapshot.getKey().equals(getGame())){
                    Responses responses=dataSnapshot.getValue(Responses.class);
                    ModifySystem(responses);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void ModifySystem(final Responses responses){
        Log.d(TAG, "ModifySystem: ++==++++++ Modifying Search +++++++==++");
    if(responses.getBlock11().equals("empty")) {
        imageView11.setImageResource(R.drawable.bluebackground);
    }
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView11.setImageResource(R.drawable.orangebackground);
                UpdateSharedPref("imageView11");
                SetSystemNormal(responses);

            }
        });

        if(responses.getBlock12().equals("empty")) {
            imageView12.setImageResource(R.drawable.bluebackground);
        }
            imageView12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView12.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView12");
                    SetSystemNormal(responses);
                    game();
                    return;
                }
            });

        if(responses.getBlock13().equals("empty")) {
            imageView13.setImageResource(R.drawable.bluebackground);
        }
            imageView13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView13.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView13");
                    SetSystemNormal(responses);
                    return;
                }

            });

        if(responses.getBlock21().equals("empty")) {
            imageView21.setImageResource(R.drawable.bluebackground);
        }
            imageView21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView21.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView21");
                    SetSystemNormal(responses);
                    game();
                    return;
                }
            });

        if(responses.getBlock22().equals("empty")) {
            imageView22.setImageResource(R.drawable.bluebackground);
        }
            imageView22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView22.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView22");
                    SetSystemNormal(responses);
                    return;
                }
            });

        if(responses.getBlock23().equals("empty")) {
            imageView23.setImageResource(R.drawable.bluebackground);
        }
            imageView23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView23.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView23");
                    SetSystemNormal(responses);
                    return;
                }
            });

        if(responses.getBlock31().equals("empty")) {
            imageView31.setImageResource(R.drawable.bluebackground);
        }
            imageView31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView31.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView31");
                    SetSystemNormal(responses);
                    return;
                }
            });

        if(responses.getBlock32().equals("empty")) {
            imageView32.setImageResource(R.drawable.bluebackground);
        }
            imageView32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView32.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView32");
                    SetSystemNormal(responses);
                    return;
                }
            });

        if(responses.getBlock33().equals("empty")) {
            imageView33.setImageResource(R.drawable.bluebackground);
        }
            imageView33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView33.setImageResource(R.drawable.orangebackground);
                    UpdateSharedPref("imageView33");
                    SetSystemNormal(responses);
                    return;
                }
            });

    }

    void UpdateSharedPref(String block){
        Log.d(TAG, "UpdateSharedPref: "+"=+++++SHARED PREFERNECE UPDATE CALLED");
        SharedPreferences sharedPreferences=getSharedPreferences("block",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit().putString("block",block.toString());
        editor.apply();

    }

    String GetSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("block",MODE_PRIVATE);
        return sharedPreferences.getString("block","0");
    }


    void SetSystemNormal(Responses responses){
        Log.d(TAG, "SetSystemNormal: "+"SET NORMAL CALLED");
        if(responses.getBlock11().equals("empty")){
            imageView11.setImageResource(0);
        }
        else if(responses.getBlock11().equals("cross")) {
            imageView11.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock11().equals("zero")) {
            imageView11.setImageResource(R.drawable.zero);

        }


        if(responses.getBlock12().equals("empty")){
            imageView12.setImageResource(0);
        }
        else if(responses.getBlock12().equals("cross")) {
            imageView12.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock12().equals("zero")) {
            imageView12.setImageResource(R.drawable.zero);

        }




        if(responses.getBlock13().equals("empty")){
            imageView13.setImageResource(0);
        }
        else if(responses.getBlock13().equals("cross")) {
            imageView13.setImageResource(R.drawable.cross);


        }
        else if(responses.getBlock13().equals("zero")) {
            imageView13.setImageResource(R.drawable.zero);

        }




        if(responses.getBlock21().equals("empty")){
            imageView21.setImageResource(0);
        }
        else if(responses.getBlock21().equals("cross")) {
            imageView21.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock21().equals("zero")) {
            imageView21.setImageResource(R.drawable.zero);


        }



        if(responses.getBlock22().equals("empty")){
            imageView22.setImageResource(0);
        }
        else if(responses.getBlock22().equals("cross")) {
            imageView22.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock22().equals("zero")) {
            imageView22.setImageResource(R.drawable.zero);

        }





        if(responses.getBlock23().equals("empty")){
            imageView23.setImageResource(0);
        }
        else if(responses.getBlock23().equals("cross")) {
            imageView23.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock23().equals("zero")) {
            imageView23.setImageResource(R.drawable.zero);

        }




        if(responses.getBlock31().equals("empty")){
            imageView31.setImageResource(0);
        }
        else if(responses.getBlock31().equals("cross")) {
            imageView31.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock31().equals("zero")) {
            imageView31.setImageResource(R.drawable.zero);

        }




        if(responses.getBlock32().equals("empty")){
            imageView32.setImageResource(0);
        }
        else if(responses.getBlock32().equals("cross")) {
            imageView32.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock32().equals("zero")) {
            imageView32.setImageResource(R.drawable.zero);

        }

        if(responses.getBlock33().equals("empty")){
            imageView33.setImageResource(0);
        }
        else if(responses.getBlock33().equals("cross")) {
            imageView33.setImageResource(R.drawable.cross);

        }
        else if(responses.getBlock33().equals("zero")) {
            imageView33.setImageResource(R.drawable.zero);

        }
       game();
    }


    void swapForPlayer1(){

        Log.d(TAG, "swapForPlayer1: "+"SWAP FOR PLAYER 1 IS CALLED ==================================");
        if(GetSharedPreferences().equals("imageView11")){
            imageView11.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block11","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });

        }
        if(GetSharedPreferences().equals("imageView12")){
            imageView12.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block12","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView13")){
            imageView13.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block13","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView21")){
            imageView21.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block21","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView22")){
            imageView22.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block22","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });

        }

        if (GetSharedPreferences().equals("imageView23")){
            imageView23.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block23","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView31")){
            imageView31.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block31","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView32")){
            imageView32.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block32","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView33")){
            imageView33.setImageResource(R.drawable.zero);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block33","zero");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

    }


    void swapForPlayer2(){
        Log.d(TAG, "swapForPlayer2: swap for player 2 is called   ==============="+GetSharedPreferences());
        if(GetSharedPreferences().equals("imageView11")){
            imageView11.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block11","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });

        }
        if(GetSharedPreferences().equals("imageView12")){
            imageView12.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block12","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView13")){
            imageView13.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block13","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView21")){
            imageView21.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block21","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView22")){
            imageView22.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block22","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });

        }

        if (GetSharedPreferences().equals("imageView23")){
            imageView23.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block23","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView31")){
            imageView31.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block31","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(GetSharedPreferences().equals("imageView32")){
            imageView32.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block32","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }

        if(getSharedPreferences().equals("imageView33")){
            imageView33.setImageResource(R.drawable.cross);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("block33","cross");
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
            databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                        return;
                    }
                }
            });
        }


    }


    void game(){
        Log.d(TAG, "game: "+"GAME CALLED");
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("MatchProcess");

        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Checking Newton;


                Log.d(TAG, "NEWTON AT IMAGE 11  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 11" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock11().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView11.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block11","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock11().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock11().equals("empty")){
                                        //Simply put cross there
                                        imageView11.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block11","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock11().equals("cross")){
                                        //make the moove
                                        imageView11.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block11","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock11().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock11().equals("empty")){
                                        //Simply put zero there
                                        imageView11.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block11","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){

                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT IMAGE 11  "+gravity);
                if(gravity.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 11"+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            //checking the turn
                            if(getGameStarter().equals(responses.getTurn())){
                                //check to allow the user to make change
                                if(getGameStarter().equals("player1")){
                                    imageView11.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block11","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }
                                                return;
                                            }
                                        }
                                    });


                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView11.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block11","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }
                                                return;
                                            }
                                        }
                                    });
                                }


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 11 "+newton+"  "+gravity);
                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE AT 11 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            //checking the turn
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo Check for the gravity value here
                                if(responses.getBlock11().equals("empty")){
                                    //check to allow the user to make change
                                    if(getGameStarter().equals("player1")){
                                        imageView11.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block11","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView11.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block11","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });







        //ImageView 12

        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 12  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 12" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock12().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView12.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock12().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock12().equals("empty")){
                                        //Simply put cross there
                                        imageView12.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock12().equals("cross")){
                                        //make the moove
                                        imageView12.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock12().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock12().equals("empty")){
                                        //Simply put zero there
                                        imageView12.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 12 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 12 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView12.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block12","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView12.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block12","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 12 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock12().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView12.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView12.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block12","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });













        //ImageView 13
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(TAG, "NEWTON AT IMAGE 13  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 12" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock13().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView13.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock13().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock13().equals("empty")){
                                        //Simply put cross there
                                        imageView13.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock13().equals("cross")){
                                        //make the moove
                                        imageView13.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock13().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock13().equals("empty")){
                                        //Simply put zero there
                                        imageView13.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 13 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 13 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView13.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block13","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView13.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block13","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 13 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock13().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView13.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView13.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block13","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });









        //ImageView 21
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 21  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 21" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock21().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView21.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock21().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock21().equals("empty")){
                                        //Simply put cross there
                                        imageView21.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock21().equals("cross")){
                                        //make the moove
                                        imageView21.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock21().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock21().equals("empty")){
                                        //Simply put zero there
                                        imageView21.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 21 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 21 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView21.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block21","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView21.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block21","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 21 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock21().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView21.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView21.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block21","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });



        //ImageView 22

        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 22  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 21" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock22().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView22.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock22().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock22().equals("empty")){
                                        //Simply put cross there
                                        imageView22.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock22().equals("cross")){
                                        //make the moove
                                        imageView22.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock22().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock22().equals("empty")){
                                        //Simply put zero there
                                        imageView22.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 22 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 12 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView22.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block22","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView22.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block22","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 22 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock22().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView22.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView22.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block22","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });




        //ImageView 23

        imageView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 23  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 23" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock23().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView23.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock23().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock23().equals("empty")){
                                        //Simply put cross there
                                        imageView23.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock23().equals("cross")){
                                        //make the moove
                                        imageView23.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock23().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock23().equals("empty")){
                                        //Simply put zero there
                                        imageView23.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 23 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 23 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView23.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block23","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView23.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block23","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 23 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock23().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView23.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView23.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block23","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });



        //ImageView 31

        imageView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 31  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 31" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock31().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView31.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock31().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock31().equals("empty")){
                                        //Simply put cross there
                                        imageView31.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock31().equals("cross")){
                                        //make the moove
                                        imageView31.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock31().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock31().equals("empty")){
                                        //Simply put zero there
                                        imageView31.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 31 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 31 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView31.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block31","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView31.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block31","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 31 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock31().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView31.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView31.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block31","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });



        //ImageView 32
        imageView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 32  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 32" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock32().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView32.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock32().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock32().equals("empty")){
                                        //Simply put cross there
                                        imageView32.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock32().equals("cross")){
                                        //make the moove
                                        imageView32.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock32().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock32().equals("empty")){
                                        //Simply put zero there
                                        imageView32.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 32 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 32 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView32.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block32","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView32.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block32","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 23 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock32().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView32.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView32.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block32","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });




        //ImageView33
        imageView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "NEWTON AT IMAGE 33  "+newton);
                if(newton.equals("true")){
                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA CHANGE FOR 33" + dataSnapshot);
                            Responses responses = dataSnapshot.getValue(Responses.class);
                            //check turn;
                            if (getGameStarter().equals(responses.getTurn())) {
                                if (getGameStarter().equals("player1")) {
                                    //check if imageview11 has zero;
                                    if(responses.getBlock33().equals("zero")){
                                        //make the moove and push to database and down the newton;
                                        imageView33.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","cross");
                                        hashMap.put("turn","player2");
                                        swapForPlayer1();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    return;
                                                }
                                            }
                                        });


                                    }
                                    if(responses.getBlock33().equals("cross")){
                                        //Deny the moove
                                        Toast.makeText(getApplicationContext(),"Dont ovverite your own moove",Toast.LENGTH_SHORT).show();
                                    }
                                    if(responses.getBlock33().equals("empty")){
                                        //Simply put cross there
                                        imageView33.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }


                                } else if (getGameStarter().equals("player2")) {


                                    if(responses.getBlock33().equals("cross")){
                                        //make the moove
                                        imageView33.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","zero");
                                        hashMap.put("turn","player2");
                                        swapForPlayer2();
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    if(responses.getBlock33().equals("zero")){
                                        //deny the moove
                                    }
                                    if(responses.getBlock33().equals("empty")){
                                        //Simply put zero there
                                        imageView33.setImageResource(R.drawable.zero);
                                        HashMap<String , Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","zero");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                    DownNewton();
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                Log.d(TAG, "GRAVITY AT 33 "+gravity);
                if(gravity.equals("true")){

                    databaseReference.child(getGame()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "SNAPSHOT AT 23 "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){
                                //todo allow the player to make the change
                                if(getGameStarter().equals("player1")){
                                    imageView33.setImageResource(R.drawable.cross);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block33","cross");
                                    hashMap.put("turn","player2");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });

                                }
                                else if(getGameStarter().equals("player2")){
                                    imageView33.setImageResource(R.drawable.zero);
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("block33","zero");
                                    hashMap.put("turn","player1");
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                    databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                gettingreadycardview.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "LUCKY NUMBER "+LuckyNumer());
                                                DownGravity();
                                                if(LuckyNumer()==2){
                                                    PushNewton();
                                                }

                                                return;
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.d(TAG, "onClick: "+"CONDITION OF BOTH OF THE AT IMAGE 23 "+newton+"  "+gravity);

                if(gravity.equals("false") && newton.equals("false")) {
                    databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "DATA SNAP  "+dataSnapshot);
                            Responses responses=dataSnapshot.getValue(Responses.class);
                            if(getGameStarter().equals(responses.getTurn())){

                                if(responses.getBlock33().equals("empty")){
                                    //todo allow the player to make the change
                                    if(getGameStarter().equals("player1")){
                                        imageView33.setImageResource(R.drawable.cross);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","cross");
                                        hashMap.put("turn","player2");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });

                                    }
                                    else if(getGameStarter().equals("player2")){
                                        imageView33.setImageResource(R.drawable.zero);
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("block33","zero");
                                        hashMap.put("turn","player1");
                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("MatchProcess");
                                        databaseReference1.child(getGame()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Pushed, Waiting reply from other player", Toast.LENGTH_SHORT).show();
                                                    gettingreadycardview.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "LUCKY NUMBER: "+LuckyNumer());
                                                    if(LuckyNumer()==1){
                                                        PushGravity();
                                                    }
                                                    if(LuckyNumer()==2){
                                                        PushNewton();
                                                    }
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                            else {
                                //todo not the players turn; so wait for the reply of other player
                                gettingreadycardview.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });

    }
    }

