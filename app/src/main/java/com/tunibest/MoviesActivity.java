package com.tunibest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tunibest.RecyclV.RecycleAdapter;
import com.tunibest.RecyclV.RecyclerViewInterface;

public class MoviesActivity extends AppCompatActivity  implements RecyclerViewInterface {
    private ImageView backArrow;
    private RecyclerView recv,recv1;
    private RecycleAdapter MyAdapter,MyAdapter2;
    int[] images = {R.drawable.red_notice,
            R.drawable.shangchi_and_the_legend_of_the_ten_rings,R.drawable.maleficent,R.drawable.maleficent_mistress_of_evil,R.drawable.predestination,
            R.drawable.black_panther};
    int[] images2 = {R.drawable.free_guy, R.drawable.deadpool,
            R.drawable.avengers_infinity_war,R.drawable.venom_let_there_be_carnage,
            R.drawable.guardians_of_the_galaxy,R.drawable.thor_ragnarok};
    String[] names = {"Red Notice","Shang-Chi and the Legend of the Ten Rings",
            "Maleficent","Maleficent Mistress Of Evil","Predestination",
            "Black Panther"};
    String[] names2 = {"Free Guy","Deadpool","Avengers Infinity War",
            "Venom: Let There Be Carnage","Guardians of the Galaxy","Thor: Ragnarok"};
    String[] Categories = {"","Action","Romance","Comedy",
            "Drama","Fantasy","Horror","Mystery","Thriller"};
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        backArrow = (ImageView) findViewById(R.id.backarrow);

         spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recv = (RecyclerView) findViewById(R.id.rvc1);
        recv1 = (RecyclerView) findViewById(R.id.rvc2);


        LinearLayoutManager VertLay1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager VetLay2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recv.setLayoutManager(VertLay1);
        recv1.setLayoutManager(VetLay2);

        MyAdapter = new RecycleAdapter(names,images,this,this);
        MyAdapter2 = new RecycleAdapter(names2,images2,this,this);
        recv.setAdapter(MyAdapter);
        recv1.setAdapter(MyAdapter2);
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

    @Override
    public void onItemSelect(int pos) {
        Log.v("TAGGE",String.valueOf(pos));
    }
}