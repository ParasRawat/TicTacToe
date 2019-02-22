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
import android.widget.TimePicker;
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
    CardView winnercardview;
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
        winnercardview=findViewById(R.id.winnercardview);

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

        //Updater LogiC
        DatabaseReference updater=FirebaseDatabase.getInstance().getReference("MatchProcess");
        updater.child(getGame()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+"Updater is called");
                Responses responses=dataSnapshot.getValue(Responses.class);
                if(!responses.getBlock11().equals("empty")){
                    if(responses.getBlock11().equals("zero")){
                        imageView11.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView11.setImageResource(R.drawable.cross);
                    }
                }

                if(!responses.getBlock12().equals("empty")){
                    if(responses.getBlock12().equals("zero")){
                        imageView12.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView12.setImageResource(R.drawable.cross);
                    }
                }

                if(!responses.getBlock13().equals("empty")){
                    if(responses.getBlock13().equals("zero")){
                        imageView13.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView13.setImageResource(R.drawable.cross);
                    }
                }
                if(!responses.getBlock21().equals("empty")){
                    if(responses.getBlock21().equals("zero")){
                        imageView21.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView21.setImageResource(R.drawable.cross);
                    }
                }
                if(!responses.getBlock22().equals("empty")){
                    if(responses.getBlock22().equals("zero")){
                        imageView22.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView22.setImageResource(R.drawable.cross);
                    }
                }
                if(!responses.getBlock23().equals("empty")){
                    if(responses.getBlock23().equals("zero")){
                        imageView23.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView23.setImageResource(R.drawable.cross);
                    }
                }
                if(!responses.getBlock31().equals("empty")){
                    if(responses.getBlock31().equals("zero")){
                        imageView31.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView31.setImageResource(R.drawable.cross);
                    }
                }
                if(!responses.getBlock32().equals("empty")){
                    if(responses.getBlock32().equals("zero")){
                        imageView32.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView32.setImageResource(R.drawable.cross);
                    }
                }
                if(!responses.getBlock33().equals("empty")){
                    if(responses.getBlock33().equals("zero")){
                        imageView33.setImageResource(R.drawable.zero);
                    }
                    else {
                        imageView33.setImageResource(R.drawable.cross);
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
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
                        Responses responses=dataSnapshot.getValue(Responses.class);
                        if(getGameStarter().equals(responses.getTurn())){

                            if(responses.getBlock11().equals("empty")){
                                //todo allow the player to make the change
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });






        //ImageView 12

        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });




        //ImageView 13
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });



        //ImageView 21
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });



        //ImageView 22

        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });




        //ImageView 23

        imageView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });



        //ImageView 31

        imageView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });



        //ImageView 32
        imageView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
        });




        //ImageView33
        imageView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getGame()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        gettingreadycardview.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
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
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"I dont know what to do",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Not a valid response, already filled ",Toast.LENGTH_LONG).show();
                                gettingreadycardview.setVisibility(View.GONE);
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
                    winnercardview.setVisibility(View.VISIBLE);

                }

                if(block21.equals(block22) && block22.equals(block23) && !block21.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    winnercardview.setVisibility(View.VISIBLE);

                }
                if(block31.equals(block32) && block32.equals(block33) && !block31.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    winnercardview.setVisibility(View.VISIBLE);

                }

                if(block11.equals(block21) && block21.equals(block31) &&  !block11.equals("empty") ){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    winnercardview.setVisibility(View.VISIBLE);

                }
                if(block12.equals(block22) && block22.equals(block32) && !block12.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    winnercardview.setVisibility(View.VISIBLE);

                }

                if(block13.equals(block23) && block23.equals(block33)  && !block13.equals("empty")) {
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    winnercardview.setVisibility(View.VISIBLE);

                }


                if(block11.equals(block22) && block22.equals(block33)  && !block11.equals("empty")){
                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
                    winnercardview.setVisibility(View.VISIBLE);


                }

                if(block13.equals(block22) && block22.equals(block31)  && !block13.equals("empty")){

                    Toast.makeText(getApplicationContext(),"WINNER",Toast.LENGTH_LONG).show();
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

    }

