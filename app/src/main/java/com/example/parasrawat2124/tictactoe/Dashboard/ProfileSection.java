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

public class ProfileSection extends AppCompatActivity {

    DatabaseReference dbref;
    public static String USERNAME="adieu";
    SharedPreferences sharedpref;
    TextView name,email,won,lost,rank,score,gravity,bull;
    ImageView imguser;
    int sc=0,rk=0,gr=0,bu=0;

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
//        sharedpref=getSharedPreferences("User",MODE_PRIVATE);
//        USERNAME=sharedpref.getString("name","");

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
                sc=(2*user.getWon())-user.getLost();
                bu=user.getWon()/3;
                gr=user.getWon()/5;
                imguser.setImageURI(Uri.parse(user.getUri()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //compute rank=number of records with higher scores than userscore + 1
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    UserProfile user=ds.getValue(UserProfile.class);
                    if(user.getScore()>=sc && !user.getUsername().equals(USERNAME)) rk++;
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
        //update computed values
        dbref.child(USERNAME).child("score").setValue(sc);
        dbref.child(USERNAME).child("rank").setValue(rk);
        dbref.child(USERNAME).child("bulls").setValue(bu);
        dbref.child(USERNAME).child("gravities").setValue(gr);

        score.setText("score : "+sc);
        rank.setText("rank : "+rk);
        bull.setText("x "+bu);
        gravity.setText("x "+gr);

    }
}
