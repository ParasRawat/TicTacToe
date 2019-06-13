package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parasrawat2124.tictactoe.Adapters.OnlineAdapter;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineFragment extends Fragment {

    String USERNAME="";
    DatabaseReference dbref;
    RecyclerView rec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_online_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        String tabname=args.getString("tabname");
        USERNAME=args.getString("username");

        rec=view.findViewById(R.id.list);
        dbref=FirebaseDatabase.getInstance().getReference("Users");

        switch (tabname){
            case "RANDOM": fetchRandom();break;
            case "FRIENDS": fetchFriends();
        }


    }

    public void fetchRandom(){

        final ArrayList<String> usernames=new ArrayList<>();
        final ArrayList<String> status=new ArrayList<>();
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String st=dataSnapshot.child("status").getValue().toString();
                if(st.equals("online")){
                    usernames.add(dataSnapshot.getKey());
                    status.add(st);
                }
                setAdapter(usernames,status);
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

    public void fetchFriends(){

        dbref.child(USERNAME).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> t=new GenericTypeIndicator<ArrayList<String>>() {};
                final ArrayList<String> frns=dataSnapshot.getValue(t);
                final ArrayList<String> status=new ArrayList<>();
                final ArrayList<String> usernames=new ArrayList<>();
                //final HashMap<String,String> map=new HashMap<>();

                for(int i=0;i<frns.size();i++){
                    final String fname=frns.get(i);
                    dbref.child(fname).child("status").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            usernames.add(fname);
                            status.add(dataSnapshot.getValue().toString());
                            //map.put(fname,dataSnapshot.getValue().toString());
                            //TODO check for duplicate entries
                            //TODO this can be optimized as adapter can be notified only the change!
                            setAdapter(usernames,status);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setAdapter(ArrayList<String> names, ArrayList<String> status){
        OnlineAdapter adapter=new OnlineAdapter(names,status);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        rec.setAdapter(adapter);
    }
}
