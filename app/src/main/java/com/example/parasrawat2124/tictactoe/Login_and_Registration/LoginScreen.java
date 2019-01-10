package com.example.parasrawat2124.tictactoe.Login_and_Registration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.CatchAndMatch.CatchPlayer;
import com.example.parasrawat2124.tictactoe.CatchAndMatch.CompleteYourProfile;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.spec.ECField;

public class LoginScreen extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient googleSignInClient;
    public static final String TAG="LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        //both email and password as well google login are added inside the app.

        //Google sign in option to request for email
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        googleSignInClient=GoogleSignIn.getClient(this, gso);

        //adding on click listner for the google sign in button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallGoogleSignIn();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
    }

    void updateUI(FirebaseUser account){
        try{
        if(!account.equals(null)){
            Toast.makeText(getApplicationContext(),"Sigining in with google was successfuull",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginScreen.this,CompleteYourProfile.class));
        }
        else {
            Toast.makeText(getApplicationContext(),"Error while signign in ",Toast.LENGTH_LONG).show();
        }}
        catch (Exception e){

        }
    }
    //Calling the google Sign in Option
    void CallGoogleSignIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //OnActivity Result methode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d(TAG, "onActivityResult: "+task);
//            handleSignInResult(task);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //account = returned after the google login
                //means google sign in was successfull, now authenticating with the firebase
                FirebaseAuthenticateWithGoogle(account);
            }
            catch (ApiException e){
                Toast.makeText(getApplicationContext(),"Signing in has been falied with result id "+e,Toast.LENGTH_LONG).show();
            }
        }
    }

    //handle sign in task
    void FirebaseAuthenticateWithGoogle(GoogleSignInAccount account){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sign in with Google Success",Toast.LENGTH_LONG).show();
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Signing in was fail",Toast.LENGTH_LONG).show();
                    updateUI(null);
                }

            }
        });

    }

}
