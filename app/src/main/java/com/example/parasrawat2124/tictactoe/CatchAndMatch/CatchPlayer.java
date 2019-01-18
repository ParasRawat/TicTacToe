package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.R;
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
    TextView playername,letsplay;
    public static final String TAG="CatchPlayer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_player);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        playername=findViewById(R.id.playername);
        letsplay=findViewById(R.id.letsplay);

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

    }
    void matchPlayer(){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        String player=playername.getText().toString();
        databaseReference.child(player).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue()!= null) {
                        Log.d(TAG, "onDataChange: "+dataSnapshot);
                        Toast.makeText(getApplicationContext(), "Successfully Found, Awaiting reply", Toast.LENGTH_SHORT).show();
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
}
