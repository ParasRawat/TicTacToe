package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.ModelClass.GamerProfile;
import com.example.parasrawat2124.tictactoe.ModelClass.MyDragEventListener;
import com.example.parasrawat2124.tictactoe.MyDragShadowBuilder;
import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Match extends AppCompatActivity {
    public static final String TAG="DRAG";
    public static final String IMAGEVIEW_TAG="icon_bitmap";
    ImageView crosss;
    ImageView zero;
    ImageView imageView11,imageView12,imageView13,imageView21,imageView22,imageView23,imageView31,imageView32,imageView33;
    String player2;
    String player1;
    CircleImageView gamer1,gamer2;
    TextView gamer1name,gamer2name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        crosss=findViewById(R.id.cross);
        zero=findViewById(R.id.zero);
        gamer1=findViewById(R.id.gamer1);
        gamer2=findViewById(R.id.gamer2);
        gamer1name=findViewById(R.id.gamer1name);
        gamer2name=findViewById(R.id.gamer2name);
        imageView11=findViewById(R.id.text11);
        imageView12=findViewById(R.id.text12);
        imageView13=findViewById(R.id.text13);
        imageView21=findViewById(R.id.text21);
        imageView22=findViewById(R.id.text22);
        imageView23=findViewById(R.id.text23);
        imageView31=findViewById(R.id.text31);
        imageView32=findViewById(R.id.text32);
        imageView33=findViewById(R.id.text33);
        crosss.setTag(IMAGEVIEW_TAG);
        zero.setTag(IMAGEVIEW_TAG);
        zero.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String i=v.getTag().toString();

                ClipData.Item item=new ClipData.Item(i);

                ClipData dragData = new ClipData(
                        v.getTag().toString(),
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(zero);
                v.startDrag(dragData,myShadow,v,0);

                return true;
            }
        });
        crosss.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String i=v.getTag().toString();

                ClipData.Item item=new ClipData.Item(i);

                ClipData dragData = new ClipData(
                        v.getTag().toString(),
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(crosss);
                v.startDrag(dragData,myShadow,v,0);
                return true;
            }
        });
        imageView11.setOnDragListener(dragListener);
        imageView12.setOnDragListener(dragListener);
        imageView13.setOnDragListener(dragListener);
        imageView21.setOnDragListener(dragListener);
        imageView22.setOnDragListener(dragListener);
        imageView23.setOnDragListener(dragListener);
        imageView31.setOnDragListener(dragListener);
        imageView32.setOnDragListener(dragListener);
        imageView33.setOnDragListener(dragListener);

        fillData();
    }

    View.OnDragListener dragListener=new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            //takes all the information of the view that is dragged
            int dragevent=event.getAction();
            switch (dragevent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DROP:
                    final View view= (View) event.getLocalState();
                    Log.d(TAG, "onDrag: "+view.getId()+"  "+R.id.cross);
                    if(view.getId()==R.id.cross) {
                        v.setBackgroundResource(R.drawable.cross);
                        //todo Check for the game is over or not
                    }
                    else if(view.getId()==R.id.zero){
                        v.setBackgroundResource(R.drawable.zero);
                        //todo check if the game is over or not

                }
                        return true;
            }
            return true;

            //todo D
        }
    };

    String getSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }
    void fillData(){
        Log.d(TAG, "fillData: "+player1);
        Log.d(TAG, "fillData: "+player2);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference.child(player1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).into(gamer1);
                gamer1name.setText(gamerProfile.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Gamers");
        databaseReference2.child(player2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot);
                GamerProfile gamerProfile=dataSnapshot.getValue(GamerProfile.class);
                Picasso.get().load(gamerProfile.getUri()).into(gamer2);
                gamer2name.setText(gamerProfile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

