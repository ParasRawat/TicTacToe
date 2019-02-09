package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.ModelClass.Matching;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CatchPlayer extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView playername,letsplay,or,requests;

    public static final String TAG="CatchPlayer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_player);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        playername=findViewById(R.id.playername);
        letsplay=findViewById(R.id.letsplay);
        or=findViewById(R.id.or);
        requests=findViewById(R.id.requests);
        Toast.makeText(getApplicationContext(),"Welcome "+firebaseUser.getDisplayName(),Toast.LENGTH_LONG);
        Log.d(TAG, "onCreate:+++++++++===========++++++++++ "+firebaseUser.getEmail());

        letsplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please provide the name",Toast.LENGTH_SHORT).show();
                }
                else {
                    matchPlayer();

                }
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CatchPlayer.this,MatchingPlayers.class);
                intent.putExtra("response","1");
                storeResponse("1");
                startActivity(intent);
            }
        });

    }
    void matchPlayer(){
        //Checking if the player is registered with in for our database
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        final String player=playername.getText().toString();
        databaseReference.child(player).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue()!= null) {
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
                        Toast.makeText(getApplicationContext(), "Successfully Found, Awaiting reply", Toast.LENGTH_SHORT).show();
                        storeSecondPlayer(player);
                        //player is the player that is searched.
                        startMatchInDatabase(getSharedPreferences(),player);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Cannot Find, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Cannot Find, Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void storeResponse(String response){
        SharedPreferences sharedPreferences=getSharedPreferences("Response",getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit().putString("response",response);
        editor.apply();
    }
    String getSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }
    void startMatchInDatabase(String player1,String player2)
    {
        //player1 is the challenger
        //player2 is the challenged
        Matching matching=new Matching(player1,player2,"","","","ready","notready");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ActiveMtach");
        databaseReference.child(player1+"vs"+player2).setValue(matching).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Match started success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CatchPlayer.this,MatchingPlayers.class);
                    intent.putExtra("response","2");
                    storeResponse("2");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error while pushing the match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void storeSecondPlayer(String player2){
        SharedPreferences sharedPreferences=getSharedPreferences("Player2",getApplicationContext().MODE_PRIVATE);
        SharedPreferences. Editor editor= sharedPreferences.edit().putString("Player2Name",player2);
        editor.apply();
    }
}
