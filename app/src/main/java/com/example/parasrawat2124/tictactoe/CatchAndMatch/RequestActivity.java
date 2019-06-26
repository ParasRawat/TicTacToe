package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.Adapters.RequestAdapter;
import com.example.parasrawat2124.tictactoe.DummyMatch;
import com.example.parasrawat2124.tictactoe.ModelClass.UserProfile;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.parasrawat2124.tictactoe.ResultActivity.CHALLENGER;

public class RequestActivity extends AppCompatActivity {

    //TODO fetch current user

    public static String CHALLENGED="",CHALLENGER="";
    DatabaseReference dbref,dbrefmatch;
    ArrayList<String> reqrec=new ArrayList<>();
    RecyclerView rec;
    ViewPager pager;
    EditText e_challenged;
    String USERNAME;
    CardView timercard;
    TextView timertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        //definitions
        //USERNAME=getGamer();
        USERNAME=getGamer();

        Button send=findViewById(R.id.b_sendreq);
        e_challenged=findViewById(R.id.e_user);
        timertext=findViewById(R.id.countdowntextview);
        timercard=findViewById(R.id.countdowncard);
        TextView user=findViewById(R.id.t_username);
        user.setText(USERNAME);

        dbref=FirebaseDatabase.getInstance().getReference("Users");

        //status=online
        dbref.child(USERNAME).child("status").setValue("online");

        //notify challenger that his request has been accepted
        dbrefmatch=FirebaseDatabase.getInstance().getReference("PlayStatus");
        if(dbrefmatch!=null){
            dbrefmatch.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String matchid=ds.getKey();
                        Log.d("requestAc",ds.getKey());
                        Log.d("requestAc",ds.getValue().toString());
                        if(matchid.contains(USERNAME)){
                            if(ds.hasChild("ChallengedStatus") &&
                                    ds.hasChild("ChallengedStatus") &&
                                    ds.hasChild("Challenger") &&
                                    ds.hasChild("Challenged")) {
                                String challengedSt=ds.child("ChallengedStatus").getValue().toString();
                                String challengerSt=ds.child("ChallengerStatus").getValue().toString();
                                String challenger=ds.child("Challenger").getValue().toString();
                                final String challenged=ds.child("Challenged").getValue().toString();
                                if(challenger.equals(USERNAME) && challengedSt.equals("ready") && challengerSt.equals("ready")){
                                    Log.d("requestAc",challengedSt+challengerSt+challenger+USERNAME);
                                    SharedPreferences sharedPreferences=getSharedPreferences("Match",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("challenger",challenger);
                                    editor.putString("challenged",challenged);
                                    editor.putString("id",challenger+"vs"+challenged);
                                    editor.apply();

                                    //TODO succeslistener
                                    dbref.child(USERNAME).child("reqsent").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            GenericTypeIndicator<ArrayList<String>> t=new GenericTypeIndicator<ArrayList<String>>() {};
                                            ArrayList<String> arrlist=dataSnapshot.getValue(t);
                                            arrlist.remove(challenged);
                                            dbref.child(USERNAME).child("reqsent").setValue(arrlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    startTimer();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //get challenged name from online adapter
        LocalBroadcastManager.getInstance(this).registerReceiver(challengedReceiver,new IntentFilter("challenged"));

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

        pager=findViewById(R.id.pager_online);
        pager.setAdapter(adapter);
        TabLayout tab=(TabLayout) findViewById(R.id.tabs);
        tab.setupWithViewPager(pager);

        //set requests recyclerview
        fetchRequests();

        //send request
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHALLENGER=USERNAME;
                if(!e_challenged.getText().toString().equals("")) CHALLENGED=e_challenged.getText().toString();
                //add in reqreceived
                if(!CHALLENGED.equals("")) {//TODO check if request is sent to oneself
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean exist=false;
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                UserProfile user=ds.getValue(UserProfile.class);
                                if (user.getUsername().equals(CHALLENGED)) exist=true;
                            }
                            if (exist){
                                dbref.child(CHALLENGER).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                        ArrayList<String> arrlist = new ArrayList<>();
                                        //TODO add this condition in all
                                        if (dataSnapshot.hasChild("reqsent"))
                                            arrlist = dataSnapshot.child("reqsent").getValue(t);
                                        //TODO check for duplicate entries
                                        if(!arrlist.contains(CHALLENGED)) {
                                            arrlist.add(CHALLENGED);
                                            dbref.child(CHALLENGER).child("reqsent").setValue(arrlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    //add in reqreceived
                                                    dbref.child(CHALLENGED).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                                            ArrayList<String> arrlist=new ArrayList<>();
                                                            if (dataSnapshot.hasChild("reqreceived"))
                                                                arrlist = dataSnapshot.child("reqreceived").getValue(t);
                                                            //TODO check for duplicate entries
                                                            if(!arrlist.contains(CHALLENGER)) {
                                                                arrlist.add(CHALLENGER);
                                                                dbref.child(CHALLENGED).child("reqreceived").setValue(arrlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        String matchid=CHALLENGER+"vs"+CHALLENGED;
                                                                        dbrefmatch.child(matchid).child("Challenger").setValue(CHALLENGER);
                                                                        dbrefmatch.child(matchid).child("Challenged").setValue(CHALLENGED);
                                                                        dbrefmatch.child(matchid).child("ChallengerStatus").setValue("ready");
                                                                        dbrefmatch.child(matchid).child("ChallengedStatus").setValue("not ready").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                Log.d("requestAc","request sent successfully");
                                                                                Toast.makeText(RequestActivity.this, "Request sent to " + CHALLENGED, Toast.LENGTH_LONG).show();
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        else Toast.makeText(RequestActivity.this,"Request already sent",Toast.LENGTH_LONG).show();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else Toast.makeText(RequestActivity.this,CHALLENGED+" does not exist",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else Toast.makeText(RequestActivity.this,"Select a Gamer to send request",Toast.LENGTH_LONG).show();
            }
        });

        //start match when accept request
        LocalBroadcastManager.getInstance(this).registerReceiver(matchReceiver,new IntentFilter("match"));

    }

    public void fetchRequests(){
        //TODO add condition for hasChild
        //only those requests appear which are currently online
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnap) {
                dbref.child(USERNAME).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserProfile curruser=dataSnapshot.getValue(UserProfile.class);
                        //check for null
                        if(curruser.getReqreceived()!=null){
                            ArrayList<String> arrlist=curruser.getReqreceived();
                            ArrayList<String> names=new ArrayList<>();
                            for(DataSnapshot ds:dataSnap.getChildren()){
                                UserProfile user=ds.getValue(UserProfile.class);
                                if(arrlist.contains(user.getUsername()) && user.getStatus().equals("online")){
                                    names.add(user.getUsername());
                                }
                            }
                            setAdapter(names);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setAdapter(ArrayList<String> names){
        TextView t=findViewById(R.id.t_no);
        t.setVisibility(View.GONE);
        rec=findViewById(R.id.rec_req);
        rec.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutman=new LinearLayoutManager(RequestActivity.this);
        layoutman.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec.setLayoutManager(layoutman);
        RequestAdapter reqadapter=new RequestAdapter(this,names,USERNAME);
        rec.setAdapter(reqadapter);
    }

    //retrieve challenged person name
    public BroadcastReceiver challengedReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CHALLENGED=intent.getStringExtra("name");
            Log.d("userselect",CHALLENGED);
        }
    };

    public BroadcastReceiver matchReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            startTimer();
        }
    };

    void startTimer() {
        //TODO utilize this time filling shared preferences
        timercard.setVisibility(View.VISIBLE);
        CountDownTimer cTimer = new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                int mil= (int) millisUntilFinished/1000;
                //Log.d(TAG, "onTick: "+mil);
                if(mil==1){
                    timertext.setText("Start");
                }
                else {
                    timertext.setText("" + mil);
                }
            }
            public void onFinish() {
                //TODO remove names from reqsent and reqrec
                //status=in match
                dbref.child(USERNAME).child("status").setValue("in match");
                startActivity(new Intent(RequestActivity.this,DummyMatch.class));
            }
        };
        cTimer.start();
    }

    String getGamer(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbref.child(USERNAME).child("status").setValue("offline");
    }
}


