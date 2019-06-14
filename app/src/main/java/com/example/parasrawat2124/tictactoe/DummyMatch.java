package com.example.parasrawat2124.tictactoe;

import android.content.SharedPreferences;
import android.graphics.YuvImage;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.parasrawat2124.tictactoe.ModelClass.DummyMatchModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DummyMatch extends AppCompatActivity {
    ArrayList<ArrayList<Integer>> grid=new ArrayList<>();
    Boolean fetchstatus=false;
    ImageView imageView11, imageView12,imageView13,imageView21,imageView22,imageView23,imageView31,imageView32,imageView33;
    ImageView sign;
    String symbol;
    GridLayout matcharea;
    Switch aSwitch;
    ImageView gravity;
    ImageView bull;
    DummyMatchModel dummyMatchModel;
    public static final String TAG="dummymatch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_match);
        sign=findViewById(R.id.sign);

        matcharea=findViewById(R.id.match_area);
        imageView11=findViewById(R.id.text11);
        imageView12=findViewById(R.id.text12);
        imageView13=findViewById(R.id.text13);
        imageView21=findViewById(R.id.text21);
        aSwitch=findViewById(R.id.powerswitch);
        imageView22=findViewById(R.id.text22);
        gravity=findViewById(R.id.gravity);
        bull=findViewById(R.id.bull);
        imageView23=findViewById(R.id.text23);
        imageView31=findViewById(R.id.text31);
        imageView32=findViewById(R.id.text32);
        imageView33=findViewById(R.id.text33);
        String challenged="paras2";
        String challenger="puru";
        final String currentuser;
        //Pushing null grid conditions
        final String matchid=challenger+"vs"+challenged;
        matcharea.setVisibility(View.GONE);
        //VERIFYING WHETHER THE USER IS CHALLENGER OR CHALLENGED


        if(getGamer().equals(challenged)){
            symbol="zero";
            sign.setImageResource(R.drawable.greencircle);
            currentuser="challenged";
        }
        else {
            symbol="cross";
            sign.setImageResource(R.drawable.redcross);
            currentuser="challenger";
        }
        //FETCHING TABLE INSTANCE
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
        databaseReference.child(matchid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 dummyMatchModel=dataSnapshot.getValue(DummyMatchModel.class);
                Log.d(TAG, "onDataChange: ====================="+"UPDATING DUMMY MODEL");
                Toast.makeText(getApplicationContext(),"Data Fetching",Toast.LENGTH_LONG).show();
                GridUpdate(dummyMatchModel.getGrid());
                matcharea.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    //VERIFY HERE WHETHER THE USER HAS THE POWER OVER HIM OR NOT
                    gravity.setVisibility(View.VISIBLE);
                    bull.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.RubberBand).duration(1000)
                            .repeat(1).playOn(gravity);
                    YoYo.with(Techniques.Tada).duration(800)
                            .repeat(1).playOn(bull);

                }else {

                    gravity.setVisibility(View.GONE);
                    bull.setVisibility(View.GONE);
                }
            }
        });

        //POWER FUNCTIONS

        gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dummyMatchModel.getTurn().equals("challenged")){
                    if(currentuser.equals("challenged")){

                        //Gravity is applied by challenged person
                        ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                       arr=ApplyGravityChalleneged(arr);
                       DummyMatchModel dummyMatchModel=new DummyMatchModel(arr,"challenged");
                       DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("DummyMatch");
                       databaseReference.child(matchid).setValue(dummyMatchModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                          Toast.makeText(getApplicationContext(),"Applied Gravity", Toast.LENGTH_SHORT).show();
                           }
                       });

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //challenger turn
                    if(currentuser.equals("challenger")){

                        //Gravity is applied by challenger
                        ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                        arr=ApplyGravityChalleneger(arr);
                        DummyMatchModel dummyMatchModel=new DummyMatchModel(arr,"challenger");
                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("DummyMatch");
                        databaseReference.child(matchid).setValue(dummyMatchModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Applied Gravity", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }else {
                        Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });





        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(0).get(0)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(0);
                            demo.set(0,1);
                            arr.set(0,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView11.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {

                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();
                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){

                        ArrayList<Integer> demo = arr.get(0);
                        demo.set(0, 2);
                        arr.set(0, demo);
                        String turn = "challenged";
                        DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                        databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    imageView11.setImageResource(R.drawable.redcross);

                            }
                        });

                    }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });






        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(0).get(1)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(0);
                            demo.set(1,1);
                            arr.set(0,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView12.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();
                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                        ArrayList<Integer> demo = arr.get(0);
                        demo.set(1, 2);
                        arr.set(0, demo);
                        String turn = "challenged";
                        DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                        databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    imageView12.setImageResource(R.drawable.redcross);

                            }
                        });

                    }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });



        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(0).get(2)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(0);
                            demo.set(2,1);
                            arr.set(0,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView13.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(0);
                            demo.set(2, 2);
                            arr.set(0, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView13.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(1).get(0)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(1);
                            demo.set(0,1);
                            arr.set(1,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView21.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(1);
                            demo.set(0, 2);
                            arr.set(1, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView21.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });

        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(1).get(1)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(1);
                            demo.set(1,1);
                            arr.set(1,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView22.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(1);
                            demo.set(1, 2);
                            arr.set(1, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView22.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(1).get(2)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(1);
                            demo.set(2,1);
                            arr.set(1,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView23.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(1);
                            demo.set(2, 2);
                            arr.set(1, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView23.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });



        imageView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(2).get(0)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(2);
                            demo.set(0,1);
                            arr.set(2,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView31.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(2);
                            demo.set(0, 2);
                            arr.set(2, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView31.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(2).get(1)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(2);
                            demo.set(1,1);
                            arr.set(2,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView32.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(2);
                            demo.set(1, 2);
                            arr.set(2, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView32.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ================"+dummyMatchModel);
                //CHECK FOR THE BLOCK IS EMPTY OR NOT
                //BY DEFAULT, THERE IS 0 IN THE GRID
                //LET ZERO-1
                //CROSS-2
                ArrayList<ArrayList<Integer>> arr=dummyMatchModel.getGrid();
                if(arr.get(2).get(2)==0){

                    if(dummyMatchModel.getTurn().equals("challenged")){

                        if(currentuser.equals("challenged")){
                            ArrayList<Integer> demo=arr.get(2);
                            demo.set(2,1);
                            arr.set(2,demo);
                            String turn="challenger";
                            DummyMatchModel dummyMatchMode=new DummyMatchModel(arr,turn);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) imageView33.setImageResource(R.drawable.greencircle);

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }else {
                        //CHALLENGER TURN
                        if(currentuser.equals("challenger")){
                            ArrayList<Integer> demo = arr.get(2);
                            demo.set(2, 2);
                            arr.set(2, demo);
                            String turn = "challenged";
                            DummyMatchModel dummyMatchMode = new DummyMatchModel(arr, turn);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DummyMatch");
                            databaseReference.child(matchid).setValue(dummyMatchMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        imageView33.setImageResource(R.drawable.redcross);

                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"Not your turn",Toast.LENGTH_SHORT).show();

                            YoYo.with(Techniques.Tada).duration(700)
                                    .repeat(1).playOn(matcharea);
                        }

                    }

                }

                else{
                    Toast.makeText(getApplicationContext(),"OCCUPIED",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    void GridUpdate(ArrayList<ArrayList<Integer>> arr){
        ArrayList<Integer> firstrow=arr.get(0);
        ArrayList<Integer> secondrow=arr.get(1);
        ArrayList<Integer> thirdrow=arr.get(2);
        checkWinner(arr);
        if(firstrow.get(0)==1){
            imageView11.setImageResource(R.drawable.greencircle);
        }
        else if(firstrow.get(0)==2){
            imageView11.setImageResource(R.drawable.redcross);
        }
        else{
            imageView11.setImageResource(0);
        }


        if(firstrow.get(1)==1){
            imageView12.setImageResource(R.drawable.greencircle);
        }
        else if(firstrow.get(1)==2){
            imageView12.setImageResource(R.drawable.redcross);
        }
        else{
            imageView12.setImageResource(0);
        }

        if(firstrow.get(2)==1){
        imageView13.setImageResource(R.drawable.greencircle);
        }
        else if(firstrow.get(2)==2){
        imageView13.setImageResource(R.drawable.redcross);
        }
        else{
            imageView13.setImageResource(0);
        }


        if(secondrow.get(0)==1){
            imageView21.setImageResource(R.drawable.greencircle);
        }
        else if(secondrow.get(0)==2){
            imageView21.setImageResource(R.drawable.redcross);
        }
        else {

            imageView21.setImageResource(0);
        }

        if(secondrow.get(1)==1){
        imageView22.setImageResource(R.drawable.greencircle);
        }
        else if(secondrow.get(1)==2){
        imageView22.setImageResource(R.drawable.redcross);
        }
        else {
            imageView22.setImageResource(0);
        }

        if(secondrow.get(2)==1){
        imageView23.setImageResource(R.drawable.greencircle);
        }
        else if(secondrow.get(2)==2){
        imageView23.setImageResource(R.drawable.redcross);
        }
        else {
            imageView23.setImageResource(0);
        }

        if(thirdrow.get(0)==1){
            imageView31.setImageResource(R.drawable.greencircle);
        }
        else if(thirdrow.get(0)==2){
            imageView31.setImageResource(R.drawable.redcross);
        }
        else {
            imageView31.setImageResource(0);
        }
        if(thirdrow.get(1)==1){
            imageView32.setImageResource(R.drawable.greencircle);
        }
        else if(thirdrow.get(1)==2){
            imageView32.setImageResource(R.drawable.redcross);
        }
        else {
            imageView32.setImageResource(0);
        }
        if(thirdrow.get(2)==1){
            imageView33.setImageResource(R.drawable.greencircle);
        }
        else if(thirdrow.get(2)==2){
            imageView33.setImageResource(R.drawable.redcross);
        }
        else {
            imageView33.setImageResource(0);
        }

    }


    String getGamer(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        return sharedPreferences.getString("name","0");

    }
    void checkWinner(ArrayList<ArrayList<Integer>> arr){
        int i11=arr.get(0).get(0);
        int i12=arr.get(0).get(1);
        int i13=arr.get(0).get(2);
        int i21=arr.get(1).get(0);
        int i22=arr.get(1).get(1);
        int i23=arr.get(1).get(2);
        int i31=arr.get(2).get(0);
        int i32=arr.get(2).get(1);
        int i33=arr.get(2).get(2);
        //HORIZONTAL WINS
        if(i11==i12 && i11==i13 && i11!=0){
            //
            blink(imageView11);
            blink(imageView12);
            blink(imageView13);
        }

        if(i21==i22 && i22==i23 && i21!=0){
        blink(imageView21);
        blink(imageView22);
        blink(imageView23);
        }

        if(i31==i32 && i32==i33 && i31!=0){

            //
            blink(imageView31);
            blink(imageView32);
            blink(imageView33);

        }


        //VERTICAL WINS
        if(i11==i21 && i21==i31 && i11!=0){

            blink(imageView11);
            blink(imageView21);
            blink(imageView31);
        }
        if(i12==i22 && i22==i32 && i12!=0){
            blink(imageView12);
            blink(imageView22);
            blink(imageView32);

        }

        if(i13==i23 && i23==i33 && i13!=0){
            blink(imageView13);
            blink(imageView23);
            blink(imageView33);

        }

        //DIAGONAL WINS

        if(i11==i22 && i22==i33 && i11!=0){
            blink(imageView11);
            blink(imageView22);
            blink(imageView33);
        }

        if(i13==i22 && i22==i31 && i13!=0){
            blink(imageView13);
            blink(imageView22);
            blink(imageView31);

        }



    }
    private void blink(final View v) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (v.getVisibility() == View.VISIBLE) {
                            v.setVisibility(View.INVISIBLE);
                        } else {
                            v.setVisibility(View.VISIBLE);
                        }
                        blink(v);
                    }
                });
            }
        }).start();
    }

    ArrayList<ArrayList<Integer>> ApplyGravityChalleneged(ArrayList<ArrayList<Integer>> arr){

        ArrayList<Integer> firstrow=arr.get(0);
        ArrayList<Integer> secondrow=arr.get(1);
        ArrayList<Integer> thirdrow=arr.get(2);
        //Comparing second row and third row
        for(int i=0;i<3;i++){
            if(thirdrow.get(i)==0 && secondrow.get(i)==1){
                thirdrow.set(i,1);
                secondrow.set(i,0);
            }
        }
        //Comparing first row and second row;
        for(int i=0;i<3;i++){

            if(secondrow.get(i)==0 && firstrow.get(i)==1){
                secondrow.set(i,1);
                firstrow.set(i,0);
            }
        }

        //Comparing second row and third row again
        for(int i=0;i<3;i++){
            if(thirdrow.get(i)==0 && secondrow.get(i)==1){
                thirdrow.set(i,1);
                secondrow.set(i,0);
            }
        }
        arr.set(0,firstrow);
        arr.set(1,secondrow);
        arr.set(2,thirdrow);
        return arr;
    }
    ArrayList<ArrayList<Integer>> ApplyGravityChalleneger(ArrayList<ArrayList<Integer>> arr){

        ArrayList<Integer> firstrow=arr.get(0);
        ArrayList<Integer> secondrow=arr.get(1);
        ArrayList<Integer> thirdrow=arr.get(2);
        //Comparing second row and third row
        for(int i=0;i<3;i++){
            if(thirdrow.get(i)==0 && secondrow.get(i)==2){
                thirdrow.set(i,2);
                secondrow.set(i,0);
            }
        }
        //Comparing first row and second row;
        for(int i=0;i<3;i++){

            if(secondrow.get(i)==0 && firstrow.get(i)==2){
                secondrow.set(i,2);
                firstrow.set(i,0);
            }
        }

        //Comparing second row and third row again
        for(int i=0;i<3;i++){
            if(thirdrow.get(i)==0 && secondrow.get(i)==2){
                thirdrow.set(i,2);
                secondrow.set(i,0);
            }
        }
        arr.set(0,firstrow);
        arr.set(1,secondrow);
        arr.set(2,thirdrow);
        return arr;
    }

}
