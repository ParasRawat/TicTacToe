package com.example.parasrawat2124.tictactoe;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parasrawat2124.tictactoe.Login_and_Registration.LoginScreen;

public class SplashScreen extends AppCompatActivity {
    ImageView ticimage;
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ticimage=findViewById(R.id.ticimage);
        Glide.with(this).asGif().load(R.drawable.uianim).into(ticimage);
        Thread timer=new Thread(){
            public void run(){
          try{
              sleep(4000);


          }
          catch (Exception e){

          }
          finally {
              startActivity(new Intent(SplashScreen.this,LoginScreen.class));
          }


            }
        };

        timer.start();
    }
}
