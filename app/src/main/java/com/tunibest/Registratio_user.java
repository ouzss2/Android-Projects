package com.tunibest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tunibest.DataBase.MyDataBase;

public class Registratio_user extends AppCompatActivity {
    FirebaseAuth auth;
    EditText user,mail,pass,verpass;
    Button Registre;
    ActionCodeSettings actionCodeSettings;
    MyDataBase db;
    ProgressBar bar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registratio_user);
        auth = FirebaseAuth.getInstance();

        db = new MyDataBase(this);

        user = (EditText) findViewById(R.id.Loginuser);
        mail = (EditText) findViewById(R.id.Email);
        pass = (EditText) findViewById(R.id.PassUser);
        verpass = (EditText) findViewById(R.id.PassUserValid);
        Registre = (Button) findViewById(R.id.toSave);
        bar = (ProgressBar) findViewById(R.id.barprog);



        Registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Empty User Name", Toast.LENGTH_SHORT).show();
                }else if(!VerifMail()){
                    Toast.makeText(getApplicationContext(), "Wrong Mail ", Toast.LENGTH_SHORT).show();
                }else if(!VerifPass()){
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                }else {
                    //Write in Base
                    String Email = mail.getText().toString();
                    String Thepass = pass.getText().toString();
                       Registre.setVisibility(View.INVISIBLE);
                       bar.setVisibility(View.VISIBLE);
                   auth.createUserWithEmailAndPassword(Email,Thepass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Log.d("TAG", "createUserWithEmail:success");
                               AddToDataBase();
                               //updateUI(user);
                           } else {
                               Registre.setVisibility(View.VISIBLE);
                               bar.setVisibility(View.INVISIBLE);
                               // If sign in fails, display a message to the user.
                               Log.w("TAG", "createUserWithEmail:failure", task.getException());
                       }
                       }
                   });
                }
            }
        });

    }

    private void AddToDataBase() {
        if(db.Createuser(user.getText().toString(),mail.getText().toString(),pass.getText().toString())){
            startActivity(new Intent(Registratio_user.this,Registration.class));
        }else{
            Toast.makeText(getApplicationContext(),"Problem Occured !",Toast.LENGTH_LONG).show();
        }
    }

    private boolean VerifPass() {
        if(pass.getText().toString().equals(verpass.getText().toString()) && pass.getText().toString().length()>8) {
            return true;
        }else{
            return false;
        }
    }
    private boolean VerifMail() {
        if (TextUtils.isEmpty(mail.getText().toString())) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }else{
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}