package com.tunibest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tunibest.RecyclV.RecycleAdapter;
import com.tunibest.RecyclV.RecycleView_Adapter;
import com.tunibest.RecyclV.RecyclerViewInterface;

public class SeriesActivity extends AppCompatActivity implements RecyclerViewInterface {
    private ImageView backArrow;

    private RecyclerView recv,recv1;
    private RecycleAdapter MyAdapter;
    private RecycleView_Adapter MyAdapter2;

    int[] images2 = {R.drawable.game_of_thrones, R.drawable.chernobyl,
            R.drawable.breaking_bad,R.drawable.stranger_things
           };

    int[] images3 = {R.drawable.arcane, R.drawable.the_witcher,
            R.drawable.la_casa_de_papel,R.drawable.the_walking_dead
    };
    String[] names = {"Game of Thrones","Chernobyl",
            "Breaking Bad","Stranger Things"};
    String[] names2 = {"Arcane","The Witcher",
            "La casa de papel","The Walking Dead"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        backArrow = (ImageView) findViewById(R.id.backarrow);
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

        MyAdapter = new RecycleAdapter(names,images2,this,this);
        MyAdapter2 = new RecycleView_Adapter(names2,images3,this,this);
        recv.setAdapter(MyAdapter);
        recv1.setAdapter(MyAdapter2);

    }

    @Override
    public void onItemSelect(int pos) {
        Log.v("Hello ",String.valueOf(pos));
        if(pos == 0){
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(SeriesActivity.this,CooseEpisode.class);
                    intent.putExtra("MovieNumber",pos);
                    startActivity(intent);

                }
            }.start();
        }else{
            Toast.makeText(getApplicationContext(), names2[pos] +" is Not Available", Toast.LENGTH_SHORT).show();
        }

    }
}