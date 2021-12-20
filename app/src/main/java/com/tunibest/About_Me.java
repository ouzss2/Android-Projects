package com.tunibest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.tunibest.Class.MoviesImageLoader;

import java.util.concurrent.ExecutionException;

public class About_Me extends AppCompatActivity {
    FirebaseUser user;
    private FirebaseAuth mAuth;
    ImageView myimg;
    TextView mytext,mailtxt;
    boolean isSet =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        myimg =(ImageView) findViewById(R.id.userpo);
        mytext = (TextView) findViewById(R.id.userText);
        mailtxt = (TextView) findViewById(R.id.userMail);

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                MoviesImageLoader mc= new MoviesImageLoader();

                Bitmap map ;
                try {
                    map = mc.execute(user.getPhotoUrl().toString()).get();
                    Log.v("TAGE",map.toString());

                    if(!isSet){
                        myimg.setImageBitmap(map);
                        isSet =true;
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mytext.setText(user.getDisplayName());
                mailtxt.setText(user.getEmail());

            }
        }else{
            Toast.makeText(getApplicationContext(), "No user is logged", Toast.LENGTH_SHORT).show();
        }



    }


}