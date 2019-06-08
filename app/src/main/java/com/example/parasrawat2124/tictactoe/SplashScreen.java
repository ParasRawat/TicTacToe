package com.example.parasrawat2124.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parasrawat2124.tictactoe.Dashboard.Dashboard;
import com.example.parasrawat2124.tictactoe.Login_and_Registration.LoginScreen;

public class SplashScreen extends AppCompatActivity {
    ImageView ticimage;
    TextView ticheading;
    public static final String TAG="splash";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ticimage=findViewById(R.id.ticimage);
        ticheading=findViewById(R.id.ticheading);
        Animation hyperspacejump=AnimationUtils.loadAnimation(this,R.anim.hypereffect);
        ticimage.startAnimation(hyperspacejump);
        Glide.with(this).asGif().load(R.drawable.uianim).into(ticimage);
        Thread timer=new Thread(){
            public void run(){
          try{
              sleep(4000);


          }
          catch (Exception e){

          }
          finally {
              if(!getGamer().equals("0")){
                  Log.d(TAG, "run: ====="+getGamer());
                  startActivity(new Intent(SplashScreen.this,Dashboard.class));
              }
              else {
                  startActivity(new Intent(SplashScreen.this, LoginScreen.class));
              }
          }


            }
        };

        timer.start();
    }


    String getGamer(){
        SharedPreferences sharedPreferences=getSharedPreferences("Gamers",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","0");
        return name;
    }




}
