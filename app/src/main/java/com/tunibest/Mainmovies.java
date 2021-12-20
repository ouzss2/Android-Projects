package com.tunibest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.tunibest.Class.Movie;
import com.tunibest.Class.MoviesImageLoader;
import com.tunibest.DataBase.MyDataBase;
import com.tunibest.RecyclV.RecycleAdapter;
import com.tunibest.RecyclV.RecycleView_Adapter;
import com.tunibest.RecyclV.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Mainmovies extends AppCompatActivity implements RecyclerViewInterface  {

    private ArrayList<String> Movies_Names = new ArrayList<>();;
    private ArrayList<Movie> Movies_Images = new ArrayList<>();;
    FirebaseStorage storage ;
    StorageReference storageRef;
    OkHttpClient client;
    String url ,Movie_Name;
    MyDataBase db;
    RecyclerView rcview,rcview2,rcview3;
    MoviesImageLoader loaderIMG;
    ArrayList<Integer> ImagesList = new ArrayList<>();
    private RecycleAdapter MyAdapter2,MyAdapter3;
    private RecycleView_Adapter MyAdapter;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    int[] images = {R.drawable.maleficent,R.drawable.maleficent_mistress_of_evil,R.drawable.predestination,
                   R.drawable.black_panther,R.drawable.deadpool};
    int[] images2 = {R.drawable.free_guy,
            R.drawable.avengers_infinity_war,R.drawable.venom_let_there_be_carnage};
    int[] images3 = {R.drawable.red_notice,
            R.drawable.shangchi_and_the_legend_of_the_ten_rings,
            R.drawable.guardians_of_the_galaxy,R.drawable.thor_ragnarok};
    String[] names = {"Maleficent","Maleficent Mistress Of Evil","Predestination",
                      "Black Panther","Deadpool"};
    String[] names2 = {"Free Guy","Avengers Infinity War","Venom: Let There Be Carnage"};
    String[] names3 = {"Red Notice","Shang-Chi and the Legend of the Ten Rings",
            "Guardians of the Galaxy","Thor: Ragnarok"};

    BottomNavigationView navbot;
    FrameLayout frameLayout;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmovies);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Movies");
        //ImagesList = Arrays.asList(images);
        navbot = (BottomNavigationView) findViewById(R.id.bot);
        frameLayout = (FrameLayout) findViewById(R.id.framelay);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadint1();
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference("UserMovies");

        db = new MyDataBase(this);
         loaderIMG = new MoviesImageLoader();
        //urlimg = findViewById(R.id.img);



        navbot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.acount){
                  //  Toast.makeText(getApplicationContext(), "Id +"+item.getItemId(), Toast.LENGTH_SHORT).show();
                    loadads();
                    new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            Intent i = new Intent(Mainmovies.this,About_Me.class);
                            startActivity(i);
                        }
                    }.start();

                }

                return true;
            }
        });

        client = new OkHttpClient();
        rcview = (RecyclerView) findViewById(R.id.myrecx);
        rcview2 = (RecyclerView) findViewById(R.id.myrecx2);
        rcview3 = (RecyclerView) findViewById(R.id.myrecx3);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcview.setLayoutManager(horizontalLayoutManagaer);
        rcview2.setLayoutManager(horizontalLayoutManagaer2);
        rcview3.setLayoutManager(horizontalLayoutManagaer3);

        loadAvailableName();
        Movies_Images.clear();
        //Movies_Images =  laodMoviesInfo();
        LoadMovies();

        MyAdapter = new RecycleView_Adapter(names,images,this,this);
        MyAdapter2 = new RecycleAdapter(names2,images2,this,this);
        MyAdapter3 = new RecycleAdapter(names3,images3,this,this);
        rcview.setAdapter(MyAdapter);
        rcview2.setAdapter(MyAdapter2);
        rcview3.setAdapter(MyAdapter3);

        //load Movies Names And Images ! 




    }

    public void loadads(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Mainmovies.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
    //Load ad1
    public void loadint1(){
        AdRequest adRequestf = new AdRequest.Builder().build();
        load2(adRequestf);
    }
    public void load2(AdRequest adRequestf){
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequestf, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                mInterstitialAd=interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadint1();
                        Log.d("TAG", "The ad was dismissed.");


                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }



    private void loadAvailableName() {

        storageRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.

                        }
                        Movies_Names.clear();
                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            Log.v("TAG two",item.getName());
                            // Log.v("TAG two",item.getPath());
                            Movies_Names.add(item.getName());

                        }
                        laodMoviesInfo();

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

    }

    private void laodMoviesInfo() {

        for(String name : Movies_Names){
            int i = name.indexOf(".");
            Request request = new Request.Builder()
                    .url("https://movie-database-imdb-alternative.p.rapidapi.com/?s="+name.substring(0,i)+"&r=json&page=1")
                    .get()
                    .addHeader("x-rapidapi-host", "movie-database-imdb-alternative.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "xxxxxxxxxxxxxxxxxxxxx")
                    .build();

                 client.newCall(request).enqueue(new Callback() {
                     @Override
                     public void onFailure(@NonNull Call call, @NonNull IOException e) {

                     }

                     @Override
                     public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                         if(response.isSuccessful()){


                         String respbody = response.body().string();

                             try {
                                 JSONObject resultJsonObject=new JSONObject(respbody);
                                 JSONArray country_detailsJsonArray = resultJsonObject.getJSONArray("Search");
                                //for (int i=0;i<country_detailsJsonArray.length();i++){
                                   // Log.v("image URL","="+i);
                                     JSONObject countryONJ=country_detailsJsonArray.getJSONObject(0);
                                      url=countryONJ.getString("Poster");
                                      Movie_Name=countryONJ.getString("Title");


                                    //mDatabase.child(Movie_Name).setValue(new MoviesImageLoader().execute(url).get());
                                      AddToList(new Movie(Movie_Name,new MoviesImageLoader().execute(url).get() ));

                                     //Log.v("image URL","="+url);
                                     //Log.v("image Name","="+Movie_Name);

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                         }
                     }
                 });

        }

    }

    private void AddToList(Movie movie) {
        Movies_Images.add(movie);
        Log.v("Tag",movie.getName());
        //urlimg.setImageBitmap(movie.getImage());


    }

    private void LoadMovies() {
        Log.v("Tag", String.valueOf(Movies_Images.isEmpty()));
        for(Movie M : Movies_Images){
            Log.v("Tag",M.getName());
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

    @Override
    public void onItemSelect(int pos) {
        loadads();
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Mainmovies.this,VideoPlayer.class);
                intent.putExtra("MovieNumber",pos);
                intent.putExtra("Movie","");
                startActivity(intent);

            }
        }.start();

    }

    public void OpenMovies(View view) {
        loadads();
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(Mainmovies.this,MoviesActivity.class);
                startActivity(i);
            }
        }.start();

    }

    public void OpenSeries(View view) {
        loadads();

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(Mainmovies.this,SeriesActivity.class);
                startActivity(i);
            }
        }.start();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quitting")
                .setMessage("Are you sure you want to close The App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }
}
