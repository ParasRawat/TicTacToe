package com.example.parasrawat2124.tictactoe.Dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.CatchAndMatch.CatchPlayer;
import com.example.parasrawat2124.tictactoe.Login_and_Registration.LoginScreen;
import com.example.parasrawat2124.tictactoe.ModelClass.GamerProfile;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Dashboard extends AppCompatActivity {
    TextView play, history, profile ,exit,logout,gamername;
    ImageView gamerimage;
    public static final String TAG="dashboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        play=findViewById(R.id.play);
        history=findViewById(R.id.history);
        profile=findViewById(R.id.profile);
        logout=findViewById(R.id.logout);
        gamerimage=findViewById(R.id.gamerimage);
        gamername=findViewById(R.id.gamername);
        exit=findViewById(R.id.exit);
        String name=getGamer();
        gamername.setText(name);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,CatchPlayer.class));
            }
        });

        //Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(gamer1);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PastMatches.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,ProfileSection.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this,LoginScreen.class));
            }
        });

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).placeholder(R.drawable.tictacplaceholder).into(gamerimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    //Holds the gamer name with reference to the email
    String getGamer(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }
}
