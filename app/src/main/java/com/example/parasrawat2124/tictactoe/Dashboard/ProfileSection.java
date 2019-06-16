package com.example.parasrawat2124.tictactoe.Dashboard;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.ModelClass.UserProfile;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileSection extends AppCompatActivity {

    DatabaseReference dbref;
    public static String USERNAME="";
    SharedPreferences sharedpref;
    TextView name,email,won,lost,rank,score,gravity,bull;
    ImageView imguser;
    int sc=0,rk=1,gr=0,bu=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_section);

        name=findViewById(R.id.t_username);
        email=findViewById(R.id.t_useremail);
        won=findViewById(R.id.t_won);
        lost=findViewById(R.id.t_lose);
        rank=findViewById(R.id.t_rank);
        score=findViewById(R.id.t_score);
        gravity=findViewById(R.id.t_gravity);
        bull=findViewById(R.id.t_bull);
        imguser=findViewById(R.id.img_user);

        //TODO fetch username from shared pref
        USERNAME=getGamer();

        dbref=FirebaseDatabase.getInstance().getReference("Users");
        //compute score,bulls,gravities
        dbref.child(USERNAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile user=dataSnapshot.getValue(UserProfile.class);
                name.setText(user.getUsername());
                email.setText(user.getEmail());
                won.setText("won : "+user.getWon());
                lost.setText("lost : "+user.getLost());
                int bullspent=user.getBullspent();
                int grspent=user.getGravityspent();
                sc=(2*user.getWon())-user.getLost();
                score.setText("score : "+sc);
                bu=user.getWon()/3;
                gr=user.getWon()/5;
                bull.setText("x "+(bu-bullspent));
                gravity.setText("x "+(gr-grspent));
                Picasso.get().load(user.getUri()).into(imguser);
                //imguser.setImageURI(Uri.parse(user.getUri()));
                //update computed values
                dbref.child(USERNAME).child("score").setValue(sc);
                dbref.child(USERNAME).child("bulls").setValue(bu-bullspent);
                dbref.child(USERNAME).child("gravities").setValue(gr-grspent);
                checkRank(sc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void checkRank(int sc){
        //compute rank=number of records with higher scores than userscore + 1
        final int score=sc;
        Log.d("userscore",""+score);
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserProfile user=dataSnapshot.getValue(UserProfile.class);
                if(user.getScore()>score && !user.getUsername().equals(USERNAME)){
                    rank.setText("rank : "+(++rk));
                    dbref.child(USERNAME).child("rank").setValue(rk);
                }
                Log.d("userscore",user.getScore()+" "+score+" "+rk);

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

    String getGamer(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }
}
