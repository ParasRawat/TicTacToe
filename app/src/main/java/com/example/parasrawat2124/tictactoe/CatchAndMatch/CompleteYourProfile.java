package com.example.parasrawat2124.tictactoe.CatchAndMatch;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat2124.tictactoe.ModelClass.GamerProfile;
import com.example.parasrawat2124.tictactoe.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompleteYourProfile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    CardView buttoncardview;
    ImageView gamerImage;
    TextView gameremail;
    TextView gamername;
    CardView browse;
    CardView opencam;
    Uri imageuri;
    String phototofile;
    public static final int PICK_IMAGE_REQUEST=1;
    public static final int CLICK_IMAGE_REQUEST=2;
    StorageReference storageReference;
    public static final String TAG="CompleteProfile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_your_profile);
        //In this activity we have the logged in google user.
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        buttoncardview=findViewById(R.id.buttoncardview);
        gamerImage=findViewById(R.id.gamerimage);
        gameremail=findViewById(R.id.gameremail);
        gamername=findViewById(R.id.gamernamenameedittext);
        browse=findViewById(R.id.browse);
        opencam=findViewById(R.id.opencam);
        storageReference=FirebaseStorage.getInstance().getReference("GamerImages");

        buttoncardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gamername.getText().toString().equals(null)){
                    UploadFile();
                }
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenFileChooser();

            }
        });

        opencam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCamera();

            }
        });

        try {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    void UploadFile(){

        if(imageuri!=null){
            final StorageReference reference=storageReference.child(System.currentTimeMillis() + "." + GetFileExtensio(imageuri));
            final UploadTask uploadTask = reference.putFile(imageuri);
            Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        String name = gamername.getText().toString();
                        String email = firebaseUser.getEmail();
                        Uri uri = task.getResult();
                        String downloadurl = uri.toString();
                        GamerProfile gamerProfile = new GamerProfile(name, email, downloadurl);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Gamers");
                        databaseReference.child(name).setValue(gamerProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Succesfully Updated Profile", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Profile update failed Please try Again", Toast.LENGTH_SHORT).show();
                                }
                            }


                        });


                    }
                }

            });

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //todo show the progress of Image Upload.
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"Gamer's Image is Mandatory",Toast.LENGTH_SHORT).show();
        }



    }

    public void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            Picasso.get().load(imageuri).into(gamerImage);
        }

        if(requestCode==CLICK_IMAGE_REQUEST&& resultCode==RESULT_OK){

            Bitmap bitmap =BitmapFactory.decodeFile(phototofile);
            gamerImage.setImageBitmap(bitmap);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
            imageuri=Uri.parse(path);
        }
    }

    public String GetFileExtensio(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    void OpenCamera() {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takepic.resolveActivity(getPackageManager()) != null) {
            File photofile = null;
            //Temporary file for our picture
            photofile = createPhotoFile();
            //Save file in that picture
            if(photofile!=null) {
                phototofile = photofile.getAbsolutePath();
                Uri photouri=FileProvider.getUriForFile(getApplicationContext(),"com.security.tic.provider",photofile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT,photouri);
                startActivityForResult(takepic,CLICK_IMAGE_REQUEST);

            }
        }
    }
    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

}
