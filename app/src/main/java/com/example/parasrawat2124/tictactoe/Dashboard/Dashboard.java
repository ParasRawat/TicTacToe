package com.example.parasrawat2124.tictactoe.Dashboard;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.parasrawat2124.tictactoe.CatchAndMatch.CatchPlayer;
import com.example.parasrawat2124.tictactoe.R;

public class Dashboard extends AppCompatActivity {
    TextView play, history, profile ,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.music);
        mediaPlayer.start();
        play=findViewById(R.id.play);
        history=findViewById(R.id.history);
        profile=findViewById(R.id.profile);
        exit=findViewById(R.id.exit);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,CatchPlayer.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,PastMatches.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,ProfileSection.class));
            }
        });
    }
}
