package com.tunibest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {

    VideoView videoView ;
    String[] Urls = {
    "https://firebasestorage.googleapis.com/v0/b/ouzzoapp.appspot.com/o/Movies%2FMaleficent.mp4?alt=media&token=496aa6ca-d929-4d74-92aa-5e0342885c8c",
            "https://firebasestorage.googleapis.com/v0/b/ouzzoapp.appspot.com/o/Movies%2FMaleficent%20Mistress%20Of%20Evil.mp4?alt=media&token=55d8e965-c381-47b9-87fc-64ca2711f97c",
    "https://firebasestorage.googleapis.com/v0/b/ouzzoapp.appspot.com/o/Movies%2FPredestination.mp4?alt=media&token=7f60e9cf-7bb1-4839-92ef-34c2607d9a66"
   , "https://firebasestorage.googleapis.com/v0/b/ouzzoapp.appspot.com/o/Movies%2FBlack%20Panther.mp4?alt=media&token=f879418b-7e8e-4acd-a62d-6db14974566a",
            "https://firebasestorage.googleapis.com/v0/b/ouzzoapp.appspot.com/o/Movies%2FDeadpool.mp4?alt=media&token=b079b6cb-f523-40e0-af6e-ed3fc0cad48b"
            };
    String[] arcane = {"https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E01.mp4?alt=media&token=4d1d4e8e-474f-4d29-9f39-7e20ff50eae3",
     "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E02.mp4?alt=media&token=84e33179-ce8c-4bfa-b43a-49aecb31fd42",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E03.mp4?alt=media&token=b169fa2c-1bb6-423e-bb98-dd3460573d20",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E04.mp4?alt=media&token=51e0c2e6-0575-46eb-b2c1-f6b35f77e3d4",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E05.mp4?alt=media&token=37d98389-6ef4-45bc-9db3-df42c3f551af",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E06.mp4?alt=media&token=d9aea620-82a2-424e-8a53-5dcd65bf8fff",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E07.mp4?alt=media&token=43ed0d3c-7b88-45b4-b123-4ad8415314f3",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E08.mp4?alt=media&token=8fa26096-5fb8-48cc-88f4-798488424a88",
    "https://firebasestorage.googleapis.com/v0/b/ouzzo-f0acc.appspot.com/o/Arcane.S01E09.mp4?alt=media&token=7d22e16a-b69c-47cf-a7a5-34bffc6a8e1b"};
    int MovieNbr;
    String Vald="";
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MovieNbr = getIntent().getIntExtra("MovieNumber",0);
        Vald = getIntent().getStringExtra("Movie");

        if(!Vald.equals("")){
            videoView= (VideoView)findViewById(R.id.videoview);
            Uri uri = Uri.parse(arcane[MovieNbr]);
            videoView.setVideoURI(uri);
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            videoView.requestFocus();
            videoView.start();
        }else{
            videoView= (VideoView)findViewById(R.id.videoview);
            Uri uri = Uri.parse(Urls[MovieNbr]);
            videoView.setVideoURI(uri);
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            videoView.requestFocus();
            videoView.start();
        }

    }

    @Override
    public void onBackPressed() {
        count++;
        if(count ==2){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Quitting")
                    .setMessage("Are you sure you want to close The App?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
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

    public void BackFast(View view) {
        videoView.pause();

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.star_on)
                .setTitle("Quitting")
                .setMessage("Are you Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        videoView.start();
                    }
                })
                .show();
    }
}