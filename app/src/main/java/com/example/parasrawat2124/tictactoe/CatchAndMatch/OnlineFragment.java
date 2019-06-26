package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.Adapters.OnlineAdapter;
import com.example.parasrawat2124.tictactoe.ModelClass.UserProfile;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineFragment extends Fragment {

    String USERNAME="";
    DatabaseReference dbref;
    RecyclerView rec;
    String tabname;
    TextView t;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_online_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        tabname=args.getString("tabname");
        USERNAME=args.getString("username");

        t=view.findViewById(R.id.t_no);
        rec=view.findViewById(R.id.list);
        dbref=FirebaseDatabase.getInstance().getReference("Users");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> frns=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    UserProfile user=ds.getValue(UserProfile.class);
                    if(user.getUsername().equals(USERNAME)){
                        frns=user.getFriends();
                    }
                }
                ArrayList<String> fnames=new ArrayList<>(),rnames=new ArrayList<>();
                ArrayList<String> fstatus=new ArrayList<>(),rstatus=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    UserProfile user=ds.getValue(UserProfile.class);
                    Log.d("userinfo","friends"+frns.toString());
                    Log.d("userinfo",user.getUsername());
                    if(frns.contains(user.getUsername())){
                        fnames.add(user.getUsername());
                        fstatus.add(user.getStatus());
                        Log.d("userinfo",user.getUsername()+"FAdded");
                    }
                    else{
                        if(user.getStatus().equals("online") && (!user.getUsername().equals(USERNAME))){
                            rnames.add(user.getUsername());
                            rstatus.add(user.getStatus());
                            Log.d("userinfo",user.getUsername()+"RAdded");
                        }
                    }
                }

                //TODO add number of users online in tab name
                switch (tabname){
                    case "RANDOM": //head.setText("Random ("+rnames.size()+")");
                            if(rnames.size()==0){
                                t.setVisibility(View.VISIBLE);
                                t.setText("NO GAMER ONLINE !");
                            }
                            else setAdapter(rnames,rstatus);
                            break;
                    case "FRIENDS": //head.setText("Friends ("+fnames.size()+")");
                        if(fnames.size()==0){
                            t.setVisibility(View.VISIBLE);
                            t.setText("MAYBE ADD MORE FRIENDS !");
                        }
                        else setAdapter(fnames,fstatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void setAdapter(ArrayList<String> names, ArrayList<String> status){
        rec.setVisibility(View.VISIBLE);
        OnlineAdapter adapter=new OnlineAdapter(names,status,getActivity());
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
