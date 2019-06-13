package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.Adapters.RequestAdapter;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    public final String USERNAME="asdfg",CHALLENGER=USERNAME;
    public static String CHALLENGED="";
    DatabaseReference dbref;
    ArrayList<String> reqrec=new ArrayList<>();
    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        //definitions
        Button send=findViewById(R.id.b_sendreq);
        TextView user=findViewById(R.id.t_username);
        user.setText(USERNAME);

        dbref=FirebaseDatabase.getInstance().getReference("Users");

        //set random and friend fragments
        FragmentPagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                OnlineFragment frag=new OnlineFragment();
                Bundle args=new Bundle();
                args.putString("tabname",this.getPageTitle(i).toString());
                args.putString("username",USERNAME);
                frag.setArguments(args);
                return frag;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0: return "RANDOM";
                    case 1: return "FRIENDS";
                }
                return null;
            }
        };

        ViewPager pager=findViewById(R.id.pager_online);
        pager.setAdapter(adapter);
//        TabLayout tab=findViewById(R.id.tabs);
//        tab.setupWithViewPager(pager);

        //set requests recyclerview
        fetchRequests();

        //send request
        CHALLENGED="asdfg";
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add in reqreceived
                dbref.child(CHALLENGED).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<String>> t=new GenericTypeIndicator<ArrayList<String>>() {};
                        ArrayList<String> arrlist=dataSnapshot.child("reqreceived").getValue(t);
                        arrlist.add(CHALLENGER);
                        //TODO check for duplicate entries
                        dbref.child(CHALLENGED).child("reqreceived").setValue(arrlist);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //add in reqsent
                dbref.child(CHALLENGER).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<String>> t=new GenericTypeIndicator<ArrayList<String>>() {};
                        ArrayList<String> arrlist=new ArrayList<>();
                        //TODO add this condition in all
                        if(dataSnapshot.hasChild("reqsent")) arrlist=dataSnapshot.child("reqsent").getValue(t);
                        //TODO check for duplicate entries
                        arrlist.add(CHALLENGED);
                        dbref.child(CHALLENGER).child("reqsent").setValue(arrlist);
                        Toast.makeText(RequestActivity.this,"Request sent to "+CHALLENGER,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void fetchRequests(){
        dbref.child(USERNAME).child("reqreceived").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> arrlist=dataSnapshot.getValue(t);
                setAdapter(arrlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setAdapter(ArrayList<String> names){
        rec=findViewById(R.id.rec_req);
        LinearLayoutManager layoutman=new LinearLayoutManager(RequestActivity.this);
        layoutman.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec.setLayoutManager(layoutman);
        RequestAdapter reqadapter=new RequestAdapter(names,CHALLENGER);
        rec.setAdapter(reqadapter);
    }
}
