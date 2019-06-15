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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.parasrawat2124.tictactoe.CatchAndMatch.CatchPlayer;
import com.example.parasrawat2124.tictactoe.CatchAndMatch.Match;
import com.example.parasrawat2124.tictactoe.CatchAndMatch.RequestActivity;
import com.example.parasrawat2124.tictactoe.DummyMatch;
import com.example.parasrawat2124.tictactoe.Dummyactivity;
import com.example.parasrawat2124.tictactoe.Login_and_Registration.LoginScreen;
import com.example.parasrawat2124.tictactoe.ModelClass.DummyMatchModel;
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

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    TextView play, history, profile ,exit,logout,gamername;
    ArrayList<ArrayList<Integer>> grid=new ArrayList<>();
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
        final String challenged="paras2";
        final String challenger="puru";
        //Pushing null grid conditions
        for(int i=0;i<3;i++){
            ArrayList<Integer> arr=new ArrayList<>();
            for(int j=0;j<3;j++){
                arr.add(0);
            }
            grid.add(arr);
        }
        gamername.setText(name);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.Tada).duration(400)
                        .playOn(play);
                DummyMatchModel dummyMatchModel=new DummyMatchModel(grid,"challenged");
                String matchid=challenger+"vs"+challenged;
                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("DummyMatch");
                databaseReference2.child(matchid).setValue(dummyMatchModel);
                startActivity(new Intent(Dashboard.this,DummyMatch.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada).duration(400)
                        .playOn(history);
                startActivity(new Intent(Dashboard.this,PastMatches.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada).duration(400)
                        .playOn(profile);
                startActivity(new Intent(Dashboard.this,ProfileSection.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada).duration(400)
                        .playOn(logout);
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


        //THESE ARE DUMMY CHANGES FOR TESTING PURPOSE ONLY


        DummyMatchModel dummyMatchModel=new DummyMatchModel(grid,"challenged");
        String matchid=challenger+"vs"+challenged;
        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("DummyMatch");
        databaseReference2.child(matchid).setValue(dummyMatchModel);


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
