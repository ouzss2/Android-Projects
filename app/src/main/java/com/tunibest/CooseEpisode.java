package com.tunibest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.tunibest.RecyclV.Rec_Arcane;
import com.tunibest.RecyclV.RecycleAdapter;
import com.tunibest.RecyclV.RecyclerViewInterface;

public class CooseEpisode extends AppCompatActivity implements RecyclerViewInterface {
    String[] nbr = {"1","2","3","4","5","6","7","8","9"};
    String[] MovieName = {"Arcane","Arcane","Arcane","Arcane","Arcane","Arcane","Arcane","Arcane","Arcane"};
    int[] imgnbr ={R.drawable.arcane,R.drawable.arcane,R.drawable.arcane,R.drawable.arcane
      ,R.drawable.arcane,R.drawable.arcane,R.drawable.arcane,R.drawable.arcane,R.drawable.arcane};

    RecyclerView rcview;
    private Rec_Arcane MyAdapter;
    int MovieNbr;
    ImageView myimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coose_episode);
        MovieNbr = getIntent().getIntExtra("MovieNumber",0);

        myimg = findViewById(R.id.theimgh);
        rcview = (RecyclerView) findViewById(R.id.myrecView);
        LinearLayoutManager VertLay1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcview.setLayoutManager(VertLay1);
        MyAdapter = new Rec_Arcane(MovieName,nbr,imgnbr,this,this);
        rcview.setAdapter(MyAdapter);
        myimg.setImageResource(R.drawable.arcane);

        Log.v("Hello ",String.valueOf(MovieNbr));
    }

    @Override
    public void onItemSelect(int pos) {
        Intent intent = new Intent(CooseEpisode.this,VideoPlayer.class);
        intent.putExtra("MovieNumber",pos);
        intent.putExtra("Movie","Arcane");
        startActivity(intent);

    }
}