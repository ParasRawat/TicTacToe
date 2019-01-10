package com.example.parasrawat2124.tictactoe.CatchAndMatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CatchPlayer extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public static final String TAG="CatchPlayer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_player);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        Toast.makeText(getApplicationContext(),"Welcome "+firebaseUser.getDisplayName(),Toast.LENGTH_LONG);
        Log.d(TAG, "onCreate:+++++++++===========++++++++++ "+firebaseUser.getEmail());
    }
}
