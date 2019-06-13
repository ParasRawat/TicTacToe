package com.example.parasrawat2124.tictactoe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dummyactivity extends AppCompatActivity {
    public static final String TAG="dummy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummyactivity);

        ArrayList<String> arr=new ArrayList<>();

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                arr.add(0+"");
            }
        }


        ArrayList<ArrayList<String>> mar=new ArrayList<>();
        mar.add(arr);
        mar.add(arr);

        final GenericTypeIndicator<ArrayList<ArrayList<String>>> t=new GenericTypeIndicator<ArrayList<ArrayList<String>>>() {};
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Demo");
        databaseReference.child("Check").setValue(mar);


        databaseReference.child("Check").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ArrayList<String>> newar=dataSnapshot.getValue(t);
                Log.d(TAG, "onDataChange: "+newar );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

//
//    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
//
//    List<String> yourStringArray = dataSnapshot.getValue(t);
}
