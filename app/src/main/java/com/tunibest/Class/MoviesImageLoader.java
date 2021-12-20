package com.tunibest.Class;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MoviesImageLoader extends AsyncTask<String,Void, Bitmap> {


    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bmap=null;
        try {
            URL myUrl = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
            con.connect();
            InputStream inptimg = con.getInputStream();
             bmap = BitmapFactory.decodeStream(inptimg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmap;
    }
}
