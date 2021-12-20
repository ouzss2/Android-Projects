package com.tunibest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Registration extends AppCompatActivity {


    EditText mail,pass;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;
    ImageView img ;
    String Message;
    SharedPreferences sharedPref ;
    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //FIrebase Instance
        mAuth = FirebaseAuth.getInstance();
         mail = (EditText) findViewById(R.id.Loginuser);
         pass = (EditText) findViewById(R.id.PassUser);

         img = (ImageView) findViewById(R.id.GoogleSi);
         img.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 signIn();
             }
         });
        sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        number = sharedPref.getInt("isLogged", 0);

        if(number ==1){

        }


         //Google
        // Configure Google Sign In
        createUser();
    }
    private void createUser(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences.Editor prefEditor = sharedPref.edit();
                            prefEditor.putInt("isLogged",1);
                            prefEditor.commit();
                            Intent p = new Intent(Registration.this,MainActivity.class);
                            startActivity(p);
                        } else {

                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }



    public void passwordReset(View view) {
        Intent i = new Intent(Registration.this ,Forget_Password.class);
        startActivity(i);
    }


    public void GoAddUser(View view) {
        Intent i = new Intent(Registration.this ,Registratio_user.class);
        startActivity(i);
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
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    public void LoginUser(View view) {
        String maillog= mail.getText().toString();
        String passto = pass.getText().toString();
        if(VerifMail() && VerifPass()){
            mAuth.signInWithEmailAndPassword(maillog,passto).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        SharedPreferences.Editor prefEditor = sharedPref.edit();
                        prefEditor.putInt("isLogged",1);
                        prefEditor.commit();
                        Intent i = new Intent(Registration.this,MainActivity.class);
                        startActivity(i);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), "Check Your Password !", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "!! "+Message , Toast.LENGTH_SHORT).show();
        }
    }
    private boolean VerifPass() {
        if(pass.getText().toString().length()>8) {
            return true;
        }else{
            Message ="Password Length <8 ";
            return false;
        }
    }
    private boolean VerifMail() {
        if (TextUtils.isEmpty(mail.getText().toString())) {
            return false;
        } else {
            Message="Check your Mail plz";
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches();
        }
    }


}