package com.example.parasrawat2124.tictactoe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.Dashboard.Dashboard;
import com.example.parasrawat2124.tictactoe.ModelClass.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    public  static final String USERNAME="adieu",CHALLENGER=USERNAME;
    DatabaseReference dbref;
    public static String CHALLENGED="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //TODO send and receive friend confirmation from opponent
        final TextView opponent=findViewById(R.id.t_oppo);
        CHALLENGED=opponent.getText().toString();
        Button  b=findViewById(R.id.b_addfr);
        Button home=findViewById(R.id.b_dash);

        dbref=FirebaseDatabase.getInstance().getReference("Users/"+USERNAME);

        //add friend
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserProfile user=dataSnapshot.getValue(UserProfile.class);
                        ArrayList<String> arrlist=new ArrayList<>();
                        if(user.getFriends()!=null){
                            arrlist=user.getFriends();
                        }
                        if(!arrlist.contains(CHALLENGED))   arrlist.add(CHALLENGED);
                        //TODO check for duplicate entries
                        dbref.child("friends").setValue(arrlist);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ResultActivity.this,Dashboard.class);
                startActivity(i);
            }
        });

        //Animation animation=AnimationUtils.loadAnimation(this,R.anim.)
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbref.child(USERNAME).child("status").setValue("offline");
    }
}
